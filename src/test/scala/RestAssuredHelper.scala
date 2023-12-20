package io.restassured.ScalaHelper

import io.restassured.RestAssured.*
import io.restassured.response.{Response, ValidatableResponse}
import io.restassured.specification.RequestSpecification

class GivenConstructor(givenBlock: RequestSpecification => RequestSpecification):
    def When(whenBlock: RequestSpecification => Response): ExpectationConstructor =
        ExpectationConstructor(givenBlock, whenBlock)

    class ExpectationConstructor(
        givenBlock: RequestSpecification => RequestSpecification,
        whenBlock:  RequestSpecification => Response,
      ):
        /**
         * Returns a validatable response that's lets you validate the response.
         * Usage example:
         * {{{
         * Given(_.params("firstName", "John")
         *  .When(_.post("/greetXML"))
         *  .Then(_.body("greeting.firstName", equalTo("John")))
         * }}}
         *
         * @return
         *   A validatable response
         */
        def Then(validatable: ValidatableResponse => Unit) =
            val appliedGiven: RequestSpecification = givenBlock.apply(`given`())
            val appliedWhen:  Response             = whenBlock.apply(appliedGiven)
            validatable.apply(appliedWhen.`then`())
        end Then
    end ExpectationConstructor
end GivenConstructor

object Given:
    /**
     * Start building the request part of the test io.restassured.specification.
     * E.g.
     * {{{
     * Given(_.params("firstName", "John"))
     *  .When(_.post("/greetXML"))
     *  .Then(_.body("greeting.firstName", equalTo("John")))
     * }}}
     * will send a POST request to "/greetXML" with request parameters
     * `firstName=John` and `lastName=Doe` and expect that the response body
     * containing JSON or XML firstName equal to John.
     *
     * @return
     *   A request specification.
     */
    def apply(givenBlock: RequestSpecification => RequestSpecification): GivenConstructor = GivenConstructor(givenBlock)

    /**
     * Start building the request part of the test io.restassured.specification.
     * E.g.
     * {{{
     * Given()
     *  .When(_.post("/greetXML"))
     *  .Then(_.body("greeting.firstName", equalTo("John")))
     * }}}
     * will send a POST request to "/greetXML" with request parameters
     * `firstName=John` and `lastName=Doe` and expect that the response body
     * containing JSON or XML firstName equal to John.
     *
     * @return
     *   A request specification.
     */
    def apply(): GivenConstructor = GivenConstructor(identity)
end Given

/**
 * Start building the DSL expression by sending a request without any parameters
 * or headers etc. E.g.
 * {{{
 * Given()
 *   .When(_.get("/x"))
 *   .Then(_.body("x.y.z1", equalTo("Z1")))
 * }}}
 * Note that if you need to add parameters, headers, cookies or other request
 * properties use the [[Given()]] method.
 *
 * @return
 *   A request sender interface that let's you call resources on the server
 */
def When(whenBlock: RequestSpecification => Response) =
    def blankGiven(givenBlock: RequestSpecification): RequestSpecification = `given`()
    Given(blankGiven).When(whenBlock)
end When
