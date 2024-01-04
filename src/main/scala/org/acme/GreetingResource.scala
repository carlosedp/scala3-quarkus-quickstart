package org.acme

import jakarta.ws.rs.{GET, Path, Produces, core}
import org.jboss.resteasy.reactive.RestQuery
import jakarta.ws.rs.DefaultValue

@Path("/hello")
class GreetingResource:
    @GET
    @Produces(Array[String](core.MediaType.TEXT_PLAIN))
    def hello() = "Hello from RESTEasy Reactive in Scala 3"

// This is an endpoint which greets a name passed via a query parameter "name"
@Path("/greet")
class GreetingEndpoint:
    @GET
    @Produces(Array[String](core.MediaType.TEXT_PLAIN))
    def greeting(@RestQuery @DefaultValue("world") name: String) =
        s"Hello $name from RESTEasy Reactive in Scala 3"
