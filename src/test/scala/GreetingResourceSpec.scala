package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
// Let's import the Scala helper to provide a more idiomatic Scala DSL
import io.restassured.ScalaHelper.*
import org.hamcrest.CoreMatchers.is

@QuarkusTest
class GreetingResourceSpec:

    @Test
    def testHelloEndpoint(): Unit =
        Given()
            .When(
                _.get("/hello")
            ).Then(res =>
                res.statusCode(200)
                res.body(is("Hello from RESTEasy Reactive in Scala 3"))
            )
