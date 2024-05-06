package org.acme

import helper.*
import io.quarkus.test.junit.QuarkusTest
import org.hamcrest.CoreMatchers.{is, containsString}
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest:

    // Endpoint test helper
    def testEndpoint(url: String, result: String) =
        Given()
            .When(req =>
                req.get(url)
            ).ThenAssert(res =>
                res.statusCode(200)
                res.body(is(result))
            )

    @Test
    def testJSONEndpoint: Unit =
        val data: String = Given()
            .When(req =>
                req.get("/greet/json")
            ).Then(res =>
                res.statusCode(200)
                res.body("message", is("Hello world from RESTEasy Reactive in Scala 3"))
            ).Extract(_.path("message"))
        assert(data == "Hello world from RESTEasy Reactive in Scala 3")

    @Test
    def testHelloEndpoint: Unit =
        testEndpoint("/hello", "Hello World from RESTEasy Reactive in Scala 3!")

    @Test
    def testGreetEndpointWithNoParam: Unit =
        testEndpoint("/greet", "Hello world from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithEmptyParam: Unit =
        testEndpoint("/greet?name=", "Hello  from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithSingleParam: Unit =
        testEndpoint("/greet?name=quarkus", "Hello quarkus from RESTEasy Reactive in Scala 3")

    @Test
    def testGreetEndpointWithMultipleParam: Unit =
        testEndpoint(
            "/greet?name=quarkus&name=scala3",
            "Hello quarkus and scala3 from RESTEasy Reactive in Scala 3",
        )

    @Test
    def testAsyncEndpoint: Unit =
        Given()
            .When(req =>
                req.get("/greet/async")
            ).ThenAssert(res =>
                res.statusCode(200)
                res.body(containsString(
                    "The sum of the 10 generated numbers is"
                ))
            )

end GreetingResourceTest
