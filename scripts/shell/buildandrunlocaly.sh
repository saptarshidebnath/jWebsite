#!/usr/bin/env bash
#
# usage

if [ -f "./pom.xml" ]; then
  mvn -B -T 2C clean install
  __mvn_rc=$?
  if [ $__mvn_rc -eq 0 ]; then
    heroku local
  fi;
  else
  echo Trigger the script from the maven project root where the pom.xml is located.
fi;
