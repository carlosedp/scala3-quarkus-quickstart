#!/bin/bash

echo "=== Jacoco coverage report ==="

awk -F, \
   '{ instructions += $4 + $5; covered += $5 } END \
      { print covered, "/", instructions, " instructions covered"; \
      print 100*covered/instructions, "% covered" }' \
   target/jacoco-report/jacoco.csv
