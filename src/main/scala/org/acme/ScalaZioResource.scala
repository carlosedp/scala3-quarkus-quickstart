package org.acme

import java.util.concurrent.CompletionStage

import helper.ZIOHelper.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.*
import zio.*

@Path("/")
class ScalaZioResource():

    @GET
    @Path("/async/zio")
    @Produces(Array(TEXT_PLAIN))
    def asyncZioGreeting(): CompletionStage[String] =
        returnSomethingDelayed("Hello from ZIO").fromZio

    // ZIO function that returns a string after a random delay
    def returnSomethingDelayed(t: String): Task[String] =
        for
            delay  <- Random.nextIntBetween(10, 500)
            _      <- ZIO.sleep(delay.milliseconds)
            output <- ZIO.succeed(s"Delayed by $delay ms: $t")
        yield output
