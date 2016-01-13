
var args = require('minimist')(process.argv.slice(2));
var gulp = require('gulp');
var map = require('map-stream');
var global = require('./global-variables');

gulp.task('pre-analyze:missing-events', function() {
  return gulp
    .src([global.bowerDir + "*/iron-a11y-keys.html",
          global.bowerDir + "*/iron-selectable.html"
          ])
    .pipe(map(function(file, cb) {
      file.contents = new Buffer(String(file.contents)
          // iron-a11y-keys lacks the fire-keys-pressed annotation.
          .replace(/(\n.*?_fireKeysPressed:)/, function(m, $1) {
             console.log("WARNING: patching " + file.relative + " event keys-pressed");
             return  "\n" +
                     "/**\n" +
                     " * @event keys-pressed\n" +
                     " * @param {Object} detail\n" +
                     " *  @param {boolean} detail.shift true if shift key is pressed\n" +
                     " *  @param {boolean} detail.ctrl true if ctrl key is pressed\n" +
                     " *  @param {boolean} detail.meta true if meta key is pressed\n" +
                     " *  @param {boolean} detail.alt true if alt key is pressed\n" +
                     " *  @param {String} detail.key the normalized key\n" +
                     " */" + $1;
          })
          // iron-selectable lacks details
          .replace(/(\n.*)(@event )(iron-select|iron-deselect|iron-activate)/g, function(m, $1, $2, $3) {
            console.log("WARNING: patching " + file.relative + " event " + $3);
            return $1 + $2 + $3 +
                   $1 + " @param {Object} detail" +
                   $1 + "  @param {Object} detail.item the item element";
          })
      );
      cb(null, file);
    }))
    .pipe(gulp.dest(global.bowerDir));
});

gulp.task('pre-analyze:missing-behaviors', function() {
  return gulp
    .src([
          global.bowerDir + "*/paper-button-behavior.html",
          global.bowerDir + "*paper-behaviors/bower.json"
          ])
    .pipe(map(function(file, cb) {
      file.contents = new Buffer(String(file.contents)
          // paper-button-behavior is bad documented
          // TODO: remove when merged:
          // https://github.com/PolymerElements/paper-behaviors/commit/1ed3301da4f9ed873e19df1e76c6291b510ce9e2
          .replace(/\/\*\* @polymerBehavior \*\//, function(m) {
             console.log("WARNING: patching " + file.relative);
             return  "/** @polymerBehavior Polymer.PaperButtonBehavior */";
          })
          .replace(/"paper-radio-button-behavior.html"/, function(m) {
             console.log("WARNING: patching " + file.relative);
             return  '"paper-inky-focus-behavior.html"';
          })
      );
      cb(null, file);
    }))
    .pipe(gulp.dest(global.bowerDir));
});

// Hydrolysis does not support yet new events syntax
gulp.task('pre-analyze:new-syntax-events', function() {
  return gulp
    .src([global.bowerDir + "*/*.html"])
    .pipe(map(function(file, cb) {
      file.contents = new Buffer(
          String(file.contents).replace(/([ \t]+\* +)@event +([\w\-]+) +\{\{(.+)\}\} *detail.*\n/g, function(m, $1, $2, $3) {
            var ret = $1 + "@event " + $2 + "\n" +
                      $1 + " @param {Object} detail\n";

            var detail = $3.split(/ *, */);
            for (i in detail) {
              ret += detail[i].replace(/(\w+): *(\w+)/g, $1 + "  @param {$2} detail.$1 \n")
            }

            console.log("WARNING: patching event: " + $2 + " in component " + file.relative);
            return ret;
        }));
      cb(null, file);
    }))
    .pipe(gulp.dest(global.bowerDir));
});

gulp.task('pre-analyze', [
                          'pre-analyze:missing-events',
                          'pre-analyze:new-syntax-events',
                          'pre-analyze:missing-behaviors'
                         ])
