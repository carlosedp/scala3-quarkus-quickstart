package org.acme

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.*
import org.jboss.resteasy.reactive.RestQuery

@Path("/")
class GreetingResource:
    @GET
    @Path("/hello")
    @Produces(Array(TEXT_PLAIN))
    def hello() = "Hello from RESTEasy Reactive in Scala 3"

    // This is an endpoint which greets a name or names passed via a query parameter "name"
    @GET
    @Path("/greet")
    @Produces(Array(TEXT_PLAIN))
    def greeting(@RestQuery @DefaultValue("world") name: Array[String]) =
        val names = name.mkString(" and ")
        s"Hello ${names} from RESTEasy Reactive in Scala 3"

    // Generates a greeting message in JSON format
    @GET
    @Path("/greet/json")
    @Produces(Array(APPLICATION_JSON))
    def greetingJson(@RestQuery @DefaultValue("world") name: Array[String]) =
        val names = name.mkString(" and ")
        s"""{"message": "Hello ${names} from RESTEasy Reactive in Scala 3"}"""
end GreetingResource
