Hummingbird Demos
======
*See [Hummingbird](https://github.com/vaadin/hummingbird) about the platform*

Instructions on how to set up a working environment to run demo projects follow below.

The project contains the following demos:

1. Simple [Addressbook demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-addressbook-polymer) using Polymer templates 
1. [Dynamic menu demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-dynamic-menu) using HTML components
1. [Hello world demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-hello-world) using Elements API
1. [Minesweeper application demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-minesweeper-component) using HTML components
1. [Minesweeper application demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-minesweeper-element) using Elements API
1. [Minesweeper application demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-minesweeper-polymer) using Polymer 2 templates
1. Web site [complext menu demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-static-menu) using HTML components
1. [Text field component demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-textfield-component) using Elements API
1. [Text field component demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-textfield-composite) using HTML components
1. [Web components demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-web-component-progress-bubble)
1. Simple [web site demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-web-site) 


#### Deprecated Demos (legacy Angular templates based)
1. Simple [Addressbook demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-addressbook) using angular templates
1. [Hello world demo](https://github.com/vaadin/hummingbird-demo/tree/master/demo-hello-world-template) using angular templates

Quick Setup
======
1. <code>git clone https://github.com/vaadin/hummingbird-demo.git</code>
1. make sure you have a TestBench license if you want to run the tests (get it otherwise on https://vaadin.com/pro/licenses)
1. <code>mvn install</code>

Running the Demos
==
1. Go to the demo application folder and run <code>mvn jetty:run</code> in a local jetty
1. Open in localhost:8080
1. Run <code>mvn install</code> if you want to get WAR file to be able to deploy it to your Java application server

Licenses
==
The source code is released under Apache 2.0.