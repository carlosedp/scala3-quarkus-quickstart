package org.acme

import jakarta.ws.rs.{GET, Path, Produces, core}

@Path("/hello")
class GreetingResource:
    @GET
    @Produces(Array[String](core.MediaType.TEXT_PLAIN))
    def hello() = "Hello from RESTEasy Reactive in Scala 3"
