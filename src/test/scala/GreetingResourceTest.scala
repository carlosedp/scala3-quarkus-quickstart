package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import org.hamcrest.CoreMatchers.is

@QuarkusTest
class GreetingResourceTest:

    @Test
    def `Greeting returns success and expected body`(): Unit =
        Given()
            .When(
                _.get("/hello")
            ).Then(res =>
                res.statusCode(200)
                res.body(is("Hello from RESTEasy Reactive in Scala 3"))
            )
