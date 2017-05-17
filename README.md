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

Running tests
=====
The unit tests for the projects can be run using
<pre><code>mvn test</code></pre>

Remote tests execution
--------
Each pull request requires validation via Travis, so before the pull request is merged
all the tests are executed remotely by Travis CI server. 
If you want to run the tests remotely for your branch but don't want to create a PR for your patch then you can use
instructions from [here](https://docs.travis-ci.com/user/triggering-builds).
There is a script <code>scripts/runTests.sh</code> which you can use to run validation tests with various options.
To be able to use the script you need:
1. *nix system or CygWin with <code>curl</code> command installed
1. Read the link [above](https://docs.travis-ci.com/user/triggering-builds): you need an API token to be able to access to Travis API.
1. Once you get your API token you can run the script via <code>runTests.sh -branch yourBranch -token yourAPItoken</code>

The command requests Travis to execute validation tests in your branch which has to be specified as a parameter.
In addition to required parameters you can provide:
1. A commit message using <code>-message commitMessage</code>. This message will appear in the Travis Job. So you can identify your job using the message.
1. The parameter <code>-javadoc</code>. The default validation executes only tests without checking javadocs.   <code>-javadoc</code> parameter requests javadoc validation in addition to the tests.
1. The parameter <code>-sonar</code>. In addition to validation (running the tests) it requests Sonar Qube sources analysis.
1. The parameter <code>-sonaronly</code>. This parameter requests Sonar Qube analysis only (without tests).

Licenses
==
The source code is released under Apache 2.0.
