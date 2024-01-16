#!/bin/bash

# Run scalafix and scalafmt on the project

# Get Scala version from pom.xml
SCALA_VERSION=$(sed -n -e 's/.*<scala\.version>\(.*\)<\/scala\.version>.*/\1/p' pom.xml)

# Compile project
echo "=== Compiling project ==="
./mvnw clean compile test-compile

# Run scalafix
echo "=== Running scalafix ==="
scalafix . --auto-classpath --scalac-options -Yrangepos --scala-version "$SCALA_VERSION"

# Run scalafmt
echo "=== Running scalafmt ==="
scalafmt .

# Test project
echo "=== Testing project ==="
./mvnw -B test
