var args = require('minimist')(process.argv.slice(2));
var gutil = require('gulp-util');

var ns = args.groupId || "com.vaadin.hummingbird";
var nspath = ns.replace(/\./g,'/');
var currentDir = process.cwd();

var clientDirBase;
var publicDirBase;
if (args.polymer) {
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
  bowerPackages: args.polymer ? ('PolymerElements/paper-elements'.split(/[, ]+/)) : (args.package ? args.package.split(/[, ]+/) : null)
};