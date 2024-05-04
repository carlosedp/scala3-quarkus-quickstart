package org.acme

import java.util.concurrent.CompletionStage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.jdk.FutureConverters.*
import scala.util.Random

import io.quarkus.logging.Log
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.*
import org.eclipse.microprofile.config.ConfigProvider
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.metrics.annotation.Counted
import org.jboss.resteasy.reactive.RestQuery
import sttp.client3.*

@Path("/")
class GreetingResource(
    // Let's inject the configuration property "greeting.message" into a variable
    @ConfigProperty(name = "greeting.message") message: String
  ):
    @GET
    @Path("/hello")
    @Counted(name = "helloCounter", description = "How many times the hello endpoint was invoked")
    @Produces(Array(TEXT_PLAIN))
    def hello() =
        // Or programatically access the configuration property greeting.suffix
        val messageSuffix = ConfigProvider.getConfig().getValue("greeting.suffix", classOf[String])
        s"Hello ${message} from RESTEasy Reactive in Scala 3${messageSuffix}"

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

    // This is an endpoint which demonstrates how to perform asynchronous operations
    @GET
    @Path("/greet/async")
    @Produces(Array(TEXT_PLAIN))
    def asyncGreeting(): CompletionStage[String] =
        val numsAmount = 10
        Log.debug(s"Generating $numsAmount numbers asynchronously...")
        val startTime = System.currentTimeMillis()
        // Generate 10 numbers asynchronously and sum them up
        val futureSum = Future.sequence((1 to numsAmount).map(_ => generateNum())).map(_.sum)
        // Get the IP address asynchronously
        val IPFuture = getOwnIP().map(_.body).recover:
            case e: Exception =>
                Log.error("Failed to get the IP address.")
                Left("Failed to get IP")

        // When both futures are complete, results will be processed in the yield
        // part of the for comprehension.
        val futures =
            for
                sum <- futureSum
                ip  <- IPFuture.map(_.merge)
            yield
                val endTime = System.currentTimeMillis() - startTime
                Log.debug(s"My IP is: $ip")
                Log.debug(s"Generated $numsAmount numbers asynchronously in ${endTime}ms")
                s"The sum of the $numsAmount generated numbers is $sum. Was generated asynchronously in ${endTime}ms.\nYour IP is: $ip."

        futures.asJava
    end asyncGreeting

    // This method demonstrates how to use Scala's Future to perform asynchronous operations
    def generateNum(): Future[Int] =
        Future:
            // Sleep for a random amount of time between 800 and 1200ms
            Thread.sleep(Random.between(800, 1200))
            42

    // This method would be used to demonstrate how to perform an HTTP request using sttp returning a Scala Future
    def getOwnIP(): Future[Response[Either[String, String]]] =
        val startTime = System.currentTimeMillis()
        val backend   = HttpClientFutureBackend(options = SttpBackendOptions.connectionTimeout(5.seconds))
        val request   = basicRequest.get(uri"https://api.ipify.org")
        Log.debug(s"Getting my IP asynchronously took ${System.currentTimeMillis() - startTime}ms.")
        backend.send(request)
end GreetingResource
