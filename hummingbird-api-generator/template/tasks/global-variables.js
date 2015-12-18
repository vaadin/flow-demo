var args = require('minimist')(process.argv.slice(2));
var gutil = require('gulp-util');

var ns = args.groupId || "com.vaadin.hummingbird";
var nspath = ns.replace(/\./g,'/');
var currentDir = process.cwd();

var clientDirBase;
var publicDirBase;
if (args.all || args.polymer || args.vaadin) {
  var parentDir = currentDir.split('/');
  parentDir.pop();
  parentDir = parentDir.join('/');
  clientDirBase = parentDir + '/hummingbird-polymer-elements-api/src/main/java' 
  publicDirBase = parentDir + '/hummingbird-polymer-elements-api/src/main/resources' 
} else {
  clientDirBase = currentDir + (args.pom ? '/pom/' : '/target/generated/') + (args.javaDir || 'src/main/java/').replace(/,+$/, "");
  publicDirBase = currentDir + (args.pom ? '/pom/' : '/target/generated/') + (args.resourcesDir || 'src/main/resources/').replace(/,+$/, "");
}

var clientDir = clientDirBase + '/' + nspath + "/";
var publicDir = publicDirBase + "/VAADIN/";
gutil.log('Using clientDir: ' + clientDir + ', publicDir: ' + publicDir);

var bowerPackages;
if (args.all) {
	bowerPackages = ('vaadin/vaadin-combo-box,PolymerElements/paper-elements'.split(/[, ]+/));
} else if (args.polymer) {
	bowerPackages = ('PolymerElements/paper-elements'.split(/[, ]+/));
} else if (args.vaadin) {
	bowerPackages = ('vaadin/vaadin-combo-box'.split(/[, ]+/));
} else { // DEFAULT
	bowerPackages = (args.package ? args.package.split(/[, ]+/) : null);
}
gutil.log('Bower Packages: '+bowerPackages);

module.exports = {
  ns: ns,
  nspath: nspath,
  artifactId: args.artifactId || "hummingbird-polymer-elements",
  currentDir: currentDir,
  clientDirBase: clientDirBase,
  publicDirBase: publicDirBase,
  clientDir: clientDir,
  publicDir: publicDir,
  bowerDir: publicDir + "bower_components/",
  bowerPackages: bowerPackages
};