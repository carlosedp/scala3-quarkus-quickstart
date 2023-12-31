package org.acme

import jakarta.ws.rs.{GET, Path, Produces, core}
import org.jboss.resteasy.reactive.RestQuery
import jakarta.ws.rs.DefaultValue

@Path("/")
class GreetingResource:
    @Path("/hello")
    @GET
    @Produces(Array[String](core.MediaType.TEXT_PLAIN))
    def hello() = "Hello from RESTEasy Reactive in Scala 3"

    // This is an endpoint which greets a name or names passed via a query parameter "name"
    @Path("/greet")
    @GET
    @Produces(Array[String](core.MediaType.TEXT_PLAIN))
    def greeting(@RestQuery @DefaultValue("world") name: Array[String]) =
        val names = name.mkString(" and ")
        s"Hello ${names} from RESTEasy Reactive in Scala 3"
