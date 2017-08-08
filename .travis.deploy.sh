#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ]
then
    ./.travis.deploy_demo_server_files.sh
    # Copy demos to the server
    scp -o "StrictHostKeyChecking no" -P 5177 */target/*.war dev@virtuallypreinstalled.com:tomcat/webapps/
fi
