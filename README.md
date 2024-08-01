# scala3-quarkus-quickstart <!-- omit in toc -->

[![Maven CI](https://github.com/carlosedp/scala3-quarkus-quickstart/actions/workflows/Maven-CI.yaml/badge.svg)](https://github.com/carlosedp/scala3-quarkus-quickstart/actions/workflows/Maven-CI.yaml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=carlosedp_scala3-quarkus-quickstart&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=carlosedp_scala3-quarkus-quickstart)
[![codecov](https://codecov.io/gh/carlosedp/scala3-quarkus-quickstart/graph/badge.svg?token=IlH0MwK3RA)](https://codecov.io/gh/carlosedp/scala3-quarkus-quickstart)

This project is a quickstart using Scala 3 and Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

To learn more about Scala and new in [Scala 3](https://docs.scala-lang.org/scala3/book/introduction.html), check-out <https://docs.scala-lang.org/scala3/new-in-scala3.html>.

## Development tools recommendation <!-- omit in toc -->

To start developing in Quarkus/Scala 3, I recommend the following tools:

- [Coursier](https://get-coursier.io/) to manage Scala tools and JVM install
- GraalVM 21 installed thru Coursier
- Install scalafmt and scalafix thru Coursier
- [Quarkus CLI](https://quarkus.io/get-started/)
- [VSCode](https://code.visualstudio.com/) as IDE
- The following VSCode Extensions
  - [Metals](https://marketplace.visualstudio.com/items?itemName=scalameta.metals) by ScalaMeta
  - [Scala Syntax](https://marketplace.visualstudio.com/items?itemName=scala-lang.scala)
  - [Scaladex search](https://marketplace.visualstudio.com/items?itemName=baccata.scaladex-search)
  - [Quarkus Tools](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-quarkus)

This repository demonstrates multiple Quarkus concepts and provides tooling, such as:

- Running automated tests with [Github Actions](https://github.com/carlosedp/scala3-quarkus-quickstart/actions/workflows/CI.yaml) on PRs and pushes
- Maven and Gradle build tools, configured and provided as a choice to the user
- Application [Config](https://quarkus.io/guides/config) as in [GreetingResource.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/main/src/main/scala/org/acme/GreetingResource.scala)
- Apache [Kafka](https://quarkus.io/guides/kafka) as in [ArticleProducerConsumer.scala/ArticleProcessor.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/tree/main/src/main/scala/org/acme/kafka)
- Database Persistence using [Scala Magnum](https://github.com/AugustNagro/magnum) as in [persistence/magnum](https://github.com/carlosedp/scala3-quarkus-quickstart/tree/main/src/main/scala/org/acme/persistence/magnum)
- Database Persistence using [Hibernate](https://quarkus.io/guides/hibernate-orm) as in [persistence/hibernate](https://github.com/carlosedp/scala3-quarkus-quickstart/tree/main/src/main/scala/org/acme/persistence/hibernate)
- Using Serialization with support for Scala types and Enums as in [Scala3ObjectMapperCustomizerTest.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/tree/main/src/test/scala/org/acme/Scala3ObjectMapperCustomizerTest.scala)
- Using [Qute templates](https://quarkus.io/guides/qu$$te) as in [UserResource.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/tree/main/src/main/scala/org/acme/persistence/magnum/UserResource.scala)
- Use of [rest-assured](https://github.com/rest-assured/rest-assured) with a [Scala 3 wrapper](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/main/src/test/scala/helper/RestAssuredHelper.scala) for testing REST as in [RestAssuredHelperTest.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/main/src/test/scala/helper/RestAssuredHelperTest.scala)
- Application [startup and shutdown customization](https://quarkus.io/guides/lifecycle) as in [Main.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/main/src/main/scala/org/acme/Main.scala)
- Application [Metrics](https://quarkus.io/guides/smallrye-metrics) for Prometheus as in [GreetingResource.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/21a6bf51007210d2ae5d6a8bb96ff36b6c88c9a6/src/main/scala/org/acme/GreetingResource.scala#L17) and [ArticleProcessor.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/21a6bf51007210d2ae5d6a8bb96ff36b6c88c9a6/src/main/scala/org/acme/kafka/ArticleProcessor.scala#L15-L16)
- Scala Futures as in [GreetingResource.scala](https://github.com/carlosedp/scala3-quarkus-quickstart/blob/main/src/main/scala/org/acme/GreetingResource.scala#L52) with async method calls using Scala sttp library.
- Docker-compose configuration providing the full stack of requirements for the demos (Kafka, database, Metrics, etc)

## Table of Contents <!-- omit in toc -->

- [Running the application in dev mode](#running-the-application-in-dev-mode)
- [Application Examples](#application-examples)
  - [Simple greet app](#simple-greet-app)
  - [Kafka Producer -\> Processor -\> Consumer](#kafka-producer---processor---consumer)
  - [Database Persistence](#database-persistence)
  - [Using Scala Futures and Zio](#using-scala-futures-and-zio)
  - [Using the full stack as a "production" application](#using-the-full-stack-as-a-production-application)
- [Running Tests](#running-tests)
- [Packaging and running the application](#packaging-and-running-the-application)
- [Creating a native executable and container](#creating-a-native-executable-and-container)
- [Tooling UI](#tooling-ui)
- [Command Matrix](#command-matrix)
- [Build Tool Usage](#build-tool-usage)
- [Customizing provided code](#customizing-provided-code)


## Running the application in dev mode

Run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev #or
./gradlew --console=plain quarkusDev
# or using the quarkus-cli (https://quarkus.io/get-started/)
quarkus dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.
> On dev mode and production mode, the Swagger UI can be opened at <http://localhost:8080/swagger-ui>.

This sample project contains multiple small "applications" that uses different libraries to show it's usage.

Open <http://localhost:8080> that shows Quarkus static demo page. It shows the endpoints that are exposed.

If running the application standalone (for example with native binary), start the Docker-compose stack with `docker-compose up -d` so all requirements are run like Kafka, Kafka-UI for management, Postgres and PGAdmin. Also user and database are created automatically.

## Application Examples

Below there are descriptions for some of the concepts demonstrated in this sample app.

### Simple greet app

The endpoints <http://localhost:8080/hello> or <http://localhost:8080/greet?name=Yourname> are written in Scala provided by the [GreetingResource.scala](./src/main/scala/org/acme/GreetingResource.scala) source file. Based on input, it returns a text text.

### Kafka Producer -> Processor -> Consumer

This sample app uses Kafka as a messaging middleware passing data between a Producer, a Consumer and a Processor. There is an HTML interface at <http://localhost:8080/articles.html> to interact with the application. The built-in Kafka UI can be seen at <http://localhost:8080/q/dev-ui/io.quarkus.quarkus-kafka-client/topics>.

The example uses Scala native `case class`es, `Enum`s and uses Jackson for serialization of these objects for processing using Kafka topics. It also generates metrics for amount of messages processed and processing time.

In this example, I force the "mismatch" of channel names in the `application.properties` config (matching names and Kafka topics) to make sure the SmallRye lib doesn't use in-memory queues instead of Kafka itself for message passing as a fallback (<https://quarkus.io/guides/kafka#in-memory-channels>).

![article submission sample](./docs/articles.png)

### Database Persistence

The persistence examples include JPA with Hibernate ORM, and the new kid on the Scala 3 block, [Magnum](https://github.com/AugustNagro/magnum).
Following the Magnum example you can easily also integrate Anorm, Slick or Doobie. Doobie is a bit more complex as you will have to bridge Cats Effect IO to CompletionStage/CompletableFuture. If you feel adventurous you can try bridging with SmallRye Mutiny Uni, which is lazy by default and more close in behaviour to Cats Effect IO.

The sample using **Scala 3 Magnum** lib lives under <http://localhost:8080/users/users-page>. Data is stored into Quarkus dev Postgres database that is started in dev mode.

![users](./docs/users.png)

Another sample using **hibernate** which is really un-Scala like, but it's there for reference. Some other Quarkus integrations might expect JPA to be present so there it is.

To view the sample, open <http://localhost:8080/tasks/tasks-page> and interact with the form.

### Using Scala Futures and Zio

You can also interop Scala `Future`s with Quarkus as it's used on multiple Scala native async libraries. If using `Await.result` inside a method (which blocks the thread which it was called from), annotate it with `@Blocking` (from `io.smallrye.common.annotation.Blocking`) to make Quarkus move the method to a separate thread pool meant for blocking operations. Otherwise (as is demonstrated on `ScalaFutureResource.scala`) return a `CompletionStage` which can be obtained from Scala Future by using `scala.jdk.FutureConverters`. This is used in the <http://localhost:8080/async/future> endpoint.

There is also a resource that executes and returns data from a Zio Effect. The example code is at `ScalaZioResource.scala` and it uses some helper method and extension from `ZioHelper.scala` for ease of use. The effect is run and it's return is converted to a native Java `CompletionStage` for Quarkus consumption.

### Using the full stack as a "production" application

The application can be built as a complete production application, in it's own Container image and executed using a `docker-compose` that contains all the application's dependencies (PostgreSQL, Kafka, Prometheus and all UIs for these tools). The Docker Composer file also builds the container image for the application in JVM mode. If desired to use the app in Native Image mode, build the image as instructed [here](#creating-a-native-executable-and-container) and use it's name in the `docker-compose.yml` file `image:` tag and comment the `build:` section to avoid building the JVM based image.

## Running Tests

To run tests, use:

```sh
# using quarkus-cli which will run in continuous testing
quarkus test
# or using Maven/Gradle
./mvnw -B verify
./gradlew test
```

Quakus tooling runs all requirements like the database, Kafka broker, etc on containers using a locally installed Docker or Podman.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

Running docker-compose (or podman-compose), builds the image using the `./src/main/docker/Dockerfile.openj9` Dockerfile and the application as a `jar`. Editing the `docker-compose-yml` file you can change the default JVM base container.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable and container

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can build the native executable in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

To build the container image with the Native Image application, use:

```sh
quarkus build --native --no-tests -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=graalvm
# or
./mvnw package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
# or
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.native.builder-image=graalvm
```

Some libraries (specially those not from Quarkus distribution) might require run-time initialization config in `quarkus.native.additional-build-args` inside `application.properties`. As an example, `scala.util.Random` required this to be able to build the GraalVM native image. To identify this behavior, GraalVM outputs an error as shown [here](https://github.com/scalameta/sbt-native-image/issues/27).

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling> and <https://quarkus.io/guides/building-native-image#creating-a-container>.

## Tooling UI

The following URLs can be used to manage the deployed tools by Quarkus or Docker-Compose stack (when used):

- App Homepage - <http://localhost:8080/>
- Swagger UI - <http://localhost:8080/swagger-ui>
- Quarkus Dev UI (only in dev mode) - <http://localhost:8080/q/dev-ui>
- Kafka UI (using docker-compose) - <http://localhost:9021>
- PGadmin (Postgres admin interface) (using docker-compose) - <http://localhost:8088>
- Prometheus collecting application and JVM metrics (using docker-compose) - <http://localhost:9090>

## Command Matrix

Below are the most used commands for development/test/package for each build tool:

| Task                      | Quarkus Command                                                | Maven Command                                                   | Gradle Command                                                                        |
| ------------------------- | -------------------------------------------------------------- | --------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| Dev Mode                  | `quarkus dev`                                                  | `./mvnw quarkus:dev`                                            | `./gradlew quarkusDev`                                                                |
| List Extensions           | `quarkus extension`                                            | `./mvnw quarkus:list-extensions`                                | `./gradlew listExtensions`                                                            |
| Add Extension             | `quarkus extension add smallrye-*`                             | `./mvnw quarkus:add-extension -Dextensions='smallrye-*'`        | `./gradlew addExtension --extensions='smallrye-*'`                                    |
| Run Tests                 | `quarkus test`                                                 | `./mvnw -B verify`                                              | `./gradlew test`                                                                      |
| Download deps offline     |                                                                | `./mvnw quarkus:go-offline`                                     | `./gradlew quarkusGoOffline`                                                          |
| Package Jar               | `quarkus build`                                                | `./mvnw install`                                                | `./gradlew build`                                                                     |
| Uber Jar                  | `quarkus build -Dquarkus.package.type=uber-jar`                | `./mvnw package -Dquarkus.package.type=uber-jar`                | `./gradlew build -Dquarkus.package.type=uber-jar`                                     |
| Native Binary             | `quarkus build --native`                                       | `./mvnw package -Dnative`                                       | `./gradlew build -Dquarkus.package.type=native`                                       |
| Native Bin (in container) | `quarkus build --native -Dquarkus.native.container-build=true` | `./mvnw package -Dnative -Dquarkus.native.container-build=true` | `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true` |

Scala lint tools like ScalaFmt and ScalaFix can be run using the lint goal/task in Maven and Gradle as:

```sh
./mvnw -Plint
#or
./gradlew lint
```

Refs:

- <https://quarkus.io/guides/maven-tooling>
- <https://quarkus.io/guides/gradle-tooling>

## Build Tool Usage

The project contains build config for both Maven and Gradle.

You can choose your tool and remove the files related to the build tool not in use:

Maven files: `.mvn mvnw mvnw.bat pom.xml`
Gradle files: `.gradle gradlew gradlew.bat build.gradle settings.gradle`

## Customizing provided code

To reuse this code as a template for your own applications, remember to change the following:

- For code analisys, create accounts on [Sonarcloud](https://sonarcloud.io) and [Codecov](https://app.codecov.io/) if desired
- For Action automations, create an account on [Mergify](https://dashboard.mergify.com/) if desired and configure the actions on `.mergify.yml`
- Update readme pointing to your own Sonarcloud (if kept), Codecov and GitHub action badges
- The codebase is very independent from each other so one could remove Kafka, Database, Greet without breaking the other functionality
- For databases, if using Hibernate, you can remove Scala 3 Magnum dependencies, if using Magnum, can remove Hibernate. All dependencies are commented in `pom.xml` or `build.gradle`
- If Sonarcloud is not needed, change:
  - Remove the GitHub action (./github/workflows/CI.yaml) cache task and update the test task removing additional sonar mvn tasks
  - Remove `sonar` properties from pom.xml
