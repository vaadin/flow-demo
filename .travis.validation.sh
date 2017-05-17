#!/bin/sh

# TRAVIS_BRANCH == target of normal commit or target of PR
# TRAVIS_REPO_SLUG == the repository, e.g. vaadin/vaadin

while [ "$1" != "" ]; do
    case $1 in
         -javadoc )      javadoc=1
                         ;;
         -sonar )        sonar=1
                         ;;
         -sonaronly )    sonaronly=1
                         ;;
    esac
    shift
done

if [ "$sonaronly" = "1" ]; then
mvn -B -e -V -Dmaven.test.skip=true -Dsonar.verbose=true -Dsonar.analysis.mode=issues -Dsonar.host.url=$SONAR_HOST -Dsonar.login=$SONAR_LOGIN org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar
exit
fi

export DISPLAY=:99.0
sh -e /etc/init.d/xvfb start

sonar_opts=""

if [ "$sonar" = "1" ]; then
sonar_opts="-Dsonar.verbose=true -Dsonar.analysis.mode=issues -Dsonar.host.url=$SONAR_HOST -Dsonar.login=$SONAR_LOGIN org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar"
fi

if [ "$javadoc" = "1" ]; then
   javadoc_opts=" -Dmaven.javadoc.skip=true"
else 
   javadoc_opts=" -Dmaven.javadoc.skip=false javadoc:javadoc"
fi

mvn -B -e -V -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE  -Dtest.excludegroup= verify $javadoc_opts $sonar_opts

