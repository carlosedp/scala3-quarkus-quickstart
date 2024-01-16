package org.acme

import helper.*
import io.quarkus.test.junit.QuarkusTest
import org.hamcrest.CoreMatchers.is
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest:

    // Endpoint test helper
    def testEndpoint(url: String, result: String) =
        Given()
            .When(
                _.get(url)
            ).Then(res =>
                res.statusCode(200)
                res.body(is(result))
            )

    @Test
    def testHelloEndpoint() =
        testEndpoint("/hello", "Hello from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithNoParam() =
        testEndpoint("/greet", "Hello world from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithEmptyParam() =
        testEndpoint("/greet?name=", "Hello  from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithSingleParam() =
        testEndpoint("/greet?name=quarkus", "Hello quarkus from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithMultipleParam() =
        testEndpoint(
            "/greet?name=quarkus&name=scala3",
            "Hello quarkus and scala3 from RESTEasy Reactive in Scala 3",
        )

end GreetingResourceTest
