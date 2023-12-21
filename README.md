# scala3-quarkus-quickstart

[![CI](https://github.com/carlosedp/scala3-quarkus-quickstart/actions/workflows/CI.yaml/badge.svg)](https://github.com/carlosedp/scala3-quarkus-quickstart/actions/workflows/CI.yaml)

This project is quickstart using Scala 3 and Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

To learn more about Scala and new in Scala 3, check-out <https://docs.scala-lang.org/scala3/new-in-scala3.html>.

## Development tools recommendation

To start developing in Quarkus/Scala 3, I recommend the following:

- [Coursier](https://get-coursier.io/) to manage Scala tools and JVM install
- GraalVM 21 installed thru Coursier
- [Quarkus CLI](https://quarkus.io/get-started/)
- [VSCode](https://code.visualstudio.com/) as IDE
- The following VSCode Extensions
  - [Metals](https://marketplace.visualstudio.com/items?itemName=scalameta.metals) by ScalaMeta
  - [Scala Syntax](https://marketplace.visualstudio.com/items?itemName=scala-lang.scala)
  - [Scaladex search](https://marketplace.visualstudio.com/items?itemName=baccata.scaladex-search)
  - [Quarkus Tools](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-quarkus)

## Running the application in dev mode

The project depends on the still-unpublished extension [quarkus-scala3](https://github.com/quarkiverse/quarkus-scala3) version (at least 0.0.2). Until it's published on Maven, build and install it locally with:

```sh
# You need maven installed
git clone https://github.com/quarkiverse/quarkus-scala3
cd quarkus-scala3
mvn install
cd ..
```

This will publish the latest SNAPSHOT version to your local machine (0.0.2-SNAPSHOT at the time of this writing).

You can then, run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
# or using the quarkus-cli (https://quarkus.io/get-started/)
quarkus dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

To run tests, use:

```sh
# using quarkus-cli which will run in continuous testing
quarkus test
```

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
