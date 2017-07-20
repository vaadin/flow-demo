#!/usr/bin/env bash

FILES=./*/error-screenshots/*
for file in $FILES
do
  echo $file
  base64 < $file
  echo
done
