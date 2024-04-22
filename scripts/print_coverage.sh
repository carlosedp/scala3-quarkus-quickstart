#!/bin/bash

echo "=== Jacoco coverage report ==="

if [ -f target/jacoco-report/jacoco.csv ]; then
   COVERAGE_FILE=target/jacoco-report/jacoco.csv
elif [ -f build/jacoco-report/jacoco.csv ]; then
   COVERAGE_FILE=build/jacoco-report/jacoco.csv
else
   echo "No coverage file found."
   exit 1
fi

awk -F, \
   '{ instructions += $4 + $5; covered += $5 } END \
      { print covered, "/", instructions, " instructions covered"; \
      print 100*covered/instructions, "% covered" }' \
   $COVERAGE_FILE
