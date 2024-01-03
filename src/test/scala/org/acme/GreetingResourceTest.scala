package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import org.hamcrest.CoreMatchers.is
import helper.*

@QuarkusTest
class GreetingResourceTest:

    @Test
    def testHelloEndpoint() =
        Given()
            .When(
                _.get("/hello")
            ).Then(res =>
                res.statusCode(200)
                res.body(is("Hello from RESTEasy Reactive in Scala 3"))
            )
