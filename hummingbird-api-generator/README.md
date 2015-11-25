# Vaadin hummingbird-api-generator

Vaadin hummingbird-api-generator is a tool that produces Hummingbird Server-side Java APIs for JavaScript libraries provided as input, assuming their APIs are decorated with JSDoc annotations.
Very incomplete. Much TODO. Such WIP

Currently the generator only supports Web Components written in Polymer 1.0 syntax. Support for other type of JavaScript sources might be added in the future.

## Installation and Usage

- Installation
```shell
$ npm install -g vaadin/hummingbird-api-generator
```
> If you've installed node and npm using `sudo`, installing packages globally will also require you to use `sudo`. See [http://givan.se/do-not-sudo-npm/](http://givan.se/do-not-sudo-npm/) how to remedy the situation.

- Generating the resourcer for bower packages installed in your bower_components folder
```shell
$ bower install my-web-component
$ hummingbird-api-generator
```
- Generating the resources for a library
```shell
$ hummingbird-api-generator --package=PolymerElements/paper-elements
```
- Generating the resources with a custom groupId and artifactId
```shell
$ hummingbird-api-generator --package=PolymerElements/paper-elements \
                    --groupId=com.foo --artifactId=bar
```
- Generating the resources for a non-maven structure
```shell
$ hummingbird-api-generator --package=PolymerElements/paper-elements
                    --javaDir=src --resourcesDir=src
```
- Packaging the result as a ready-to-use jar file inside /pom folder
```shell
$ hummingbird-api-generator --package=PolymerElements/paper-elements --pom
$ cd pom
$ mvn package
```
- Cleaning generated sources from src/main/java/ src/main/resources/ and pom/ folders
$ mvn clean

> Bower can be configured by placing a `.bowerrc` into the folder where `hummingbird-api-generator` command is run.

## Pre-built packages

### Paper and Iron elements

Build script, demo and usage instructions for the project are available TODO [here](https://github.com/vaadin/hummingbird-demo).
