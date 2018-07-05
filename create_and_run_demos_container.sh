#!/usr/bin/env bash
docker build -t flow-demos .
docker rm flow-demos
docker run -p 8080:8080 -d --name flow-demos flow-demos
