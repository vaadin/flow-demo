#!/usr/bin/env bash

# TRAVIS_PULL_REQUEST == "false" for a normal branch commit, the PR number for a PR
# TRAVIS_BRANCH == target of normal commit or target of PR
# TRAVIS_SECURE_ENV_VARS == true if encrypted variables, e.g. SONAR_HOST is available
# TRAVIS_REPO_SLUG == the repository, e.g. vaadin/vaadin

# Exclude the bower_components and node_modules from Sonar analysis
SONAR_EXCLUSIONS=**/bower_components/**,**/node_modules/**,**/node/**

if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ "$TRAVIS_SECURE_ENV_VARS" == "true" ]
then
    # Pull request inside repository, secure vars available (needed for Sonar and TestBench tests)
    # Run verify + Sonar analysis
    mvn -B -e -V \
        -Dmaven.javadoc.skip=false \
        -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE \
        -Dtest.excludegroup= \
        -Dsonar.verbose=true \
        -Dsonar.analysis.mode=issues \
        -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.host.url=$SONAR_HOST \
        -Dsonar.github.oauth=$SONAR_GITHUB_OAUTH \
        -Dsonar.login=$SONAR_LOGIN \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
        -Dsonar.exclusions=$SONAR_EXCLUSIONS \
        clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]
then
    # master build
    mvn -B -e -V \
        -Pall-tests \
        -Dvaadin.productionMode=true \
        -Dmaven.javadoc.skip=false \
        -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE \
        clean org.jacoco:jacoco-maven-plugin:prepare-agent install

    STATUS=$?
    if [ $STATUS -eq 0 ]
    then
        # run sonar
        echo "Running Sonar"
        mvn -B -e -V \
            -Dmaven.javadoc.skip=false \
            -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE \
            -Dsonar.analysis.mode=publish \
            -Dsonar.exclusions=$SONAR_EXCLUSIONS \
            -Dsonar.verbose=true \
            -Dsonar.host.url=$SONAR_HOST \
            -Dsonar.login=$SONAR_LOGIN \
            -DskipTests \
            compile sonar:sonar
    else
        echo "Build failed, skipping sonar."
        exit 1
    fi
else
    # Branch build, secure vars available (needed for TestBench tests)
    mvn -B -e -V \
        -Dmaven.javadoc.skip=false \
        -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE \
        -Dsonar.exclusions=$SONAR_EXCLUSIONS \
        -Dtest.excludegroup= \
        verify
fi
