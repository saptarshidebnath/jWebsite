#!/bin/bash
SCRIPTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
java -jar $SCRIPTDIR/../../../target/dependency/webapp-runner.jar target/*.war
