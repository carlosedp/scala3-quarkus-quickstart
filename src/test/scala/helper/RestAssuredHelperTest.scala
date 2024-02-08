package helper

import io.restassured.RestAssured
import io.restassured.builder.ResponseBuilder
import io.restassured.filter.Filter
import io.restassured.http.ContentType.JSON
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class RestAssuredScalaExtensionsTest:

    @BeforeEach
    def `rest assured is configured`: Unit =
        RestAssured.filters {
            (_,
                _,
                _,
              ) =>
                new ResponseBuilder()
                    .setStatusCode(200)
                    .setContentType(JSON)
                    .setBody("""{ "message" : "Hello World"}""")
                    .build()
        }

    @Test
    def `rest assured is reset after each test`: Unit =
        RestAssured.reset()

    @Test
    def `basic rest assured scala extensions are compilable`: Unit =
        Given(req =>
            req.header("Header", "Header")
            req.body("hello")
        )
            .When(
                _.put("/the/path")
            )
            .Then(res =>
                res.statusCode(200)
                res.body("message", equalTo("Hello World"))
            )

    @Test
    def `extraction with rest assured scala extensions`: Unit =
        val message: String = Given(req =>
            req.header("Header", "Header")
            req.body("hello")
        )
            .When(
                _.put("/the/path")
            )
            .Extract(
                _.path("message")
            )
        assert(message == "Hello World")

    @Test
    def `extraction after 'then', when path is not used in 'Then',  with rest assured scala extensions`: Unit =
        val message: String = Given(req =>
            req.header("Header", "Header")
            req.body("hello")
            req.filter(
                (_,
                    _,
                    _,
                  ) =>
                    new ResponseBuilder()
                        .setStatusCode(200)
                        .setContentType(JSON)
                        .setBody("""{ "message" : "Hello World"}""")
                        .build()
            )
        )
            .When(
                _.put("/the/path")
            )
            .Then(
                _.statusCode(200)
            )
            .Extract(
                _.path("message")
            )
        assert(message == "Hello World")
    end `extraction after 'then', when path is not used in 'Then',  with rest assured scala extensions`

    @Test
    def `extraction after 'then', when path is used in 'Then',  with rest assured scala extensions`: Unit =
        val message: String = Given(req =>
            req.header("Header", "Header")
            req.body("hello")
            req.filter(
                (_,
                    _,
                    _,
                  ) =>
                    new ResponseBuilder()
                        .setStatusCode(200)
                        .setContentType(JSON)
                        .setBody("""{ "message" : "Hello World"}""")
                        .build()
            )
        )
            .When(
                _.put("/the/path")
            )
            .Then(res =>
                res.statusCode(200)
                res.body("message", not(emptyOrNullString()))
            )
            .Extract(
                _.path("message")
            )
        assert(message == "Hello World")
    end `extraction after 'then', when path is used in 'Then',  with rest assured scala extensions`

    @Test
    def `all expectations error messages are included in the error message`: Unit =
        try
            Given(req =>
                req.header("Header", "Header")
                req.body("hello")
            )
                .When(
                    _.put("/the/path")
                )
                .Then(res =>
                    res.statusCode(400)
                    res.body("message", equalTo("Another World"))
                    res.body("message", equalTo("Brave new world"))
                )
        catch
            case e: AssertionError =>
                assert(e.getMessage().contains(
                    """3 expectations failed.
                      |Expected status code <400> but was <200>.
                      |
                      |JSON path message doesn't match.
                      |Expected: Another World
                      |  Actual: Hello World
                      |
                      |JSON path message doesn't match.
                      |Expected: Brave new world
                      |  Actual: Hello World
                      |""".stripMargin
                ))
end RestAssuredScalaExtensionsTest
