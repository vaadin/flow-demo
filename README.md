Flow Demos
======
*See [Flow](https://github.com/vaadin/flow) about the platform*

Instructions on how to set up a working environment to run demo projects follow below.

The project contains several sub modules (module per demo).

Quick Setup
======
1. <code>git clone https://github.com/vaadin/flow-demo.git</code>
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
