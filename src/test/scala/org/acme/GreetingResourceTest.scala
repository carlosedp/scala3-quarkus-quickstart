package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.scala.extensions.*
import org.hamcrest.CoreMatchers.{containsString, is}
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest:

    // Endpoint test helper
    // The syntax here is more verbose that exposes the `req` and `res` objects
    // to add request methods and response validations
    def testEndpoint(url: String, result: String) =
        Given()
            .When(req =>
                req.get(url)
            ).ThenAssert(res =>
                res.statusCode(200)
                res.body(is(result))
            )

    @Test
    // The syntax here is more concise and hides the `req` and `res` objects
    // using `_` and chaining the validation methods
    def testJSONEndpoint: Unit =
        val data: String = Given()
            .When(_.get("/greet/json"))
            .Then(_.statusCode(200).body("message", is("Hello world from RESTEasy Reactive in Scala 3")))
            .Extract(_.path("message"))
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
            .When(_.get("/async/future"))
            .ThenAssert(_.statusCode(200).body(containsString("The sum of the 10 generated numbers is")))

    @Test
    def testAsyncEndpointBlocking: Unit =
        Given()
            .When(_.get("/async/futureblocking"))
            .ThenAssert(
                _.statusCode(200).body(containsString("The sum of the 10 asynchronously generated numbers is 420"))
            )

    @Test
    def testAsyncEndpointZio: Unit =
        Given()
            .When(_.get("/async/zio"))
            .ThenAssert(_.statusCode(200).body(containsString("Hello from ZIO")))

end GreetingResourceTest
