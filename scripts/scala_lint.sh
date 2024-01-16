#!/bin/bash

# Run scalafix and scalafmt on the project

# Get Scala version from pom.xml
SCALA_VERSION=$(sed -n -e 's/.*<scala\.version>\(.*\)<\/scala\.version>.*/\1/p' pom.xml)

# Compile project
./mvnw clean compile test-compile

# Run scalafix
scalafix . --auto-classpath --scalac-options -Yrangepos --scala-version "$SCALA_VERSION"

# Run scalafmt
scalafmt .

# Test project
./mvnw -B test
