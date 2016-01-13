"use strict";

var args = require('minimist')(process.argv.slice(2));
var gulp = require('gulp');
var bower = require('gulp-bower')
var map = require('map-stream');
var fs = require('fs-extra');
var globalVar = require('./template/tasks/global-variables');
var gutil = require('gulp-util');
var _ = require('lodash');
var runSequence = require('run-sequence');
var hyd = require("hydrolysis");
var jsonfile = require('jsonfile');
var StreamFromArray = require('stream-from-array');
var rename = require("gulp-rename");
var marked = require('marked');

var libDir = __dirname + '/lib/';
var tplDir = __dirname + '/template/';

var helpers = require(tplDir + "helpers");
require('require-dir')(tplDir + 'tasks');

// Using global because if we try to pass it to templates via the helper or any object
// we need to call merge which makes a copy of the structure per template slowing down
// the performance.
global.parsed = []; // we store all parsed objects so as we can iterate or find behaviors

gulp.task('clean:target', function() {
  fs.removeSync(globalVar.clientDir);
});

gulp.task('clean:resources', function() {
  fs.removeSync(globalVar.publicDir);
});

gulp.task('clean', ['clean:target', 'clean:resources']);

gulp.task('bower:configure', ['clean:resources'], function(done) {
  jsonfile.readFile('.bowerrc', function (err, obj) {
    if (!err) {
      fs.copySync('.bowerrc', globalVar.publicDir + '/.bowerrc');
      if(obj.directory) {
        globalVar.bowerDir = globalVar.publicDir + '/' + obj.directory;
      }
    }
    done();
  });
});

gulp.task('bower:install', ['clean', 'bower:configure'], function() {
  if (globalVar.bowerPackages) {
    return bower({ cmd: 'install', cwd: globalVar.publicDir}, [globalVar.bowerPackages]);
  } else {
    gutil.log('No --package provided. Using package(s) from bower_components folder.');
    return gulp.src('./bower_components/**/*', {base: '.'}).pipe(gulp.dest(globalVar.publicDir));
  }
});

gulp.task('parse', ['analyze'], function(cb) {
  global.parsed.forEach(function(item) {
    if (!helpers.isBehavior(item) && item.behaviors && item.behaviors.length) {

      item.behaviors.forEach(function(name) {
        var nestedBehaviors = helpers.getNestedBehaviors(item, name);
        item.properties = _.union(item.properties, nestedBehaviors.properties);

        // merge events
        if (nestedBehaviors.events && nestedBehaviors.events.length) {
          nestedBehaviors.events.forEach(function (event) {
            var notDuplicate = _.filter(item.events, function (e) {
                return e.name == event.name;
              }).length == 0;
            if (notDuplicate) {
              item.events.push(event);
            }
          });
        }
      });
    }
    // We don't want to wrap any private api
    helpers.removePrivateApi(item.properties, 'name');
  });
  cb();
});

gulp.task('analyze', ['clean:target', 'pre-analyze'], function() {
  return gulp.src([globalVar.bowerDir + "*/*.html",
    // vaadin elements
    globalVar.bowerDir + "*/vaadin-*/vaadin-*.html",
    // ignore all demo.html, index.html and metadata.html files
    "!" + globalVar.bowerDir + "*/*demo.html",
    "!" + globalVar.bowerDir + "*/*index.html",
    "!" + globalVar.bowerDir + "*/*metadata.html",
    // includes a set of js files only, and some do not exist
    "!" + globalVar.bowerDir + "*/*web-animations.html",
    // Not useful in gwt and also has spurious event names
    "!" + globalVar.bowerDir + "*/*iron-jsonp-library.html",
    ])
    .pipe(map(function(file, cb) {
      hyd.Analyzer.analyze(globalVar.bowerDir + file.relative).then(function(result) {
        var jsonArray = _.union(result.elements, result.behaviors);
        jsonArray.forEach(function(item) {
          var path = file.relative.replace(/\\/, '/');
          if (item.is) {
            item.name = item.is;
            item.path = path;

            var bowerFile = file.base + path.split("/")[0] + "/bower.json";
            var bowerFileContent = fs.readFileSync(bowerFile);
            item.bowerData = bowerFileContent ? JSON.parse(bowerFileContent) : {};

            // Save all items in an array for later processing
            global.parsed.push(item);
          }
        });
        cb(null, file);
      })
      ['catch'](function(e){
        gutil.log(e.stack);
        cb(null, file);
      });
    }));
});

// Parse a template and generates a .java file.
// template: file in the templates folder
// obj:      context object for the template
// name:     name of the item parsed
// dir:      folder relative to the client folder to write the file
// suffix:   extra suffix for the name
function parseTemplate(template, obj, name, dir, suffix) {
  var className = helpers.camelCase(name) + suffix;
  // If there is a base .java file we extend it.
  var classBase = helpers.camelCase(name) + suffix + "Base";

  // We have to compute the appropriate name-space for the component.
  var prefix =
    // For events we prefer the first word of the name if they are standard ones.
    /^Event/.test(suffix) && /^(polymer|iron|paper|neon)-/.test(name) ? name :
    // Otherwise we try the name from its bower.json, then the sub-folder name in
    // bower_components, and finally from its name.
    obj.bowerData && obj.bowerData.name || obj.path.replace(/.*\/+(.+)\/+[^\/]+/, '$1') || name;
  //  Then we take the first part before first dash
  prefix = prefix.split('-')[0].replace(/\./g,'');

  obj.ns = globalVar.ns + '.' + prefix;

  var targetPath = globalVar.clientDir + prefix + '/' + dir;
  var targetFile = targetPath + className + ".java";
  fs.ensureFileSync(targetFile);

  var baseFile = libDir + globalVar.nspath + '/' + prefix + '/' + dir + classBase + ".java";
  if (!fs.existsSync(baseFile)) {
    var baseFile = './lib/' + globalVar.nspath + '/' + prefix + '/' + dir + classBase + ".java";
  }
  if (fs.existsSync(baseFile)) {
    obj.base = classBase;
    gutil.log("Copying: ", baseFile);
    fs.copySync(baseFile, targetPath + classBase + ".java");
  } else {
    obj.base = '';
  }

  gutil.log("Generating: ", targetFile);
  var tpl = _.template(fs.readFileSync(tplDir + template + '.template'));
  fs.writeFileSync(targetFile, new Buffer(tpl(_.merge({}, null, obj, helpers))));
}

gulp.task('generate:behaviors', ['parse'], function() {
  return StreamFromArray(global.parsed,{objectMode: true})
   .on('data', function(item) {
     if (helpers.isBehavior(item)) {
       parseTemplate('Behavior', item, item.is, '', '');
     }
   })
});

gulp.task('generate:events', ['parse'], function() {
  return StreamFromArray(global.parsed,{objectMode: true})
   .on('data', function(item) {
      if (item.events) {
        item.events.forEach(function(event) {
          event.bowerData = item.bowerData;
          parseTemplate('Event', event, event.name, 'event/', 'Event');
        });
      }
   })
});

gulp.task('generate:components', ['parse'], function() {
  return StreamFromArray(global.parsed,{objectMode: true})
   .on('data', function(item) {
      if (!helpers.isBehavior(item)) {
        parseTemplate('Component', item, item.is, '', '');
      }
   })
});

gulp.task('generate:components-all', ['generate:events','generate:components']);

gulp.task('generate', ['generate:components-all'], function() {
  gutil.log('Done.');
});

gulp.task('copy:lib', function() {
  if (!args.excludeLib) {
    gutil.log('Exporting lib');
    return gulp.src(libDir + '**')
      .pipe(gulp.dest(globalVar.clientDirBase));
  }
});

gulp.task('copy:pom', function() {
  var tpl = _.template(fs.readFileSync(tplDir + "pom.template"));
  var pom = globalVar.currentDir + "/pom/pom.xml";

  // Try to get some configuration from a package.json
  // otherwise use default values
  var pkgFile = globalVar.currentDir + 'package.json';
  globalVar.pkg = {};
  try {
    var pkgContent = fs.readFileSync(pkgFile);
    globalVar.pkg = JSON.parse(pkgContent);
  } catch(ignore) {
  }
  globalVar.pkg.pom = globalVar.pkg.pom || {};
  globalVar.pkg.pom.version = args.version || globalVar.pkg.pom.version || globalVar.pkg.version;

  fs.ensureFileSync(pom);
  fs.writeFileSync(pom, new Buffer(tpl(_.merge({}, null, globalVar, helpers))));
});

gulp.task('copy:bowerjson', function() {
	  var tpl = _.template(fs.readFileSync(tplDir + "bower.template"));
	  var bowerjson = globalVar.generatedDirBase + "/bower.json";
	  var dependencies = '';
	  globalVar.bowerPackages.forEach( function(bowerDep) {
		  if (dependencies.length > 0) dependencies += ",\n    ";
		  dependencies += "\"";
		  dependencies += bowerDep;
		  dependencies += "\": \"";
		  dependencies += bowerDep;
		  dependencies += "\"";
	  });
	  gutil.log("Writing dependencies for bower.json: " + dependencies);

	  globalVar.bowerjsondeps = dependencies;

	  fs.ensureFileSync(bowerjson);
	  fs.writeFileSync(bowerjson, new Buffer(tpl(_.merge({}, null, globalVar, helpers))));
});

gulp.task('default', function(){
  if(args.pom) {
    runSequence('clean', 'bower:install', 'generate', 'copy:lib', 'copy:pom');
  } else {
    runSequence('clean', 'bower:install', 'generate', 'copy:lib');
  }
});
