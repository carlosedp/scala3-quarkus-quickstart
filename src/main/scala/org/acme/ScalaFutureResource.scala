package org.acme

import java.util.concurrent.CompletionStage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.jdk.FutureConverters.*
import scala.util.Random

import io.quarkus.logging.Log
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.*
import sttp.client3.*

@Path("/")
class ScalaFutureResource():
    /**
     * This is an endpoint which demonstrates how to perform asynchronous
     * operations. If you call `Await.result` inside this method, annotate it
     * with @Blocking to make Quarkus move the method to a separate thread pool
     * meant for blocking operations. Otherwise (as is demonstrated here) return
     * a `CompletionStage` which can be obtained from Scala Future by using
     * `scala.jdk.FutureConverters`
     */
    @GET
    @Path("/async/future")
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
        val futures: Future[String] =
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

    @GET
    @Path("/async/futureblocking")
    @Produces(Array(TEXT_PLAIN))
    @Blocking
    def asyncGreetingBlocking(): String =
        val futureSum = Future.sequence((1 to 10).map(_ => generateNum())).map(_.sum)
        val sum       = Await.result(futureSum, 10.seconds)
        s"The sum of the 10 asynchronously generated numbers is $sum."

    // ----- Helper methods -----

    // This method demonstrates how to use Scala's Future to perform asynchronous operations
    def generateNum(): Future[Int] =
        Future:
            // Sleep for a random amount of time between 800 and 1200ms
            Thread.sleep(Random.between(800, 1200))
            42

    // This method is used to demonstrate how to perform an HTTP request using sttp returning a Scala Future
    def getOwnIP(): Future[Response[Either[String, String]]] =
        val startTime = System.currentTimeMillis()
        val backend   = HttpClientFutureBackend(options = SttpBackendOptions.connectionTimeout(5.seconds))
        val request   = basicRequest.get(uri"https://api.ipify.org")
        Log.debug(s"Getting my IP asynchronously took ${System.currentTimeMillis() - startTime}ms.")
        backend.send(request)
end ScalaFutureResource
