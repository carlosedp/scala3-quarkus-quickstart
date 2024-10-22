package org.acme.kafka.helloworld

import java.util.concurrent.TimeUnit

import io.quarkus.logging.Log
import io.quarkus.scheduler.Scheduled

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.reactive.messaging.{Channel, Emitter}

// This is a simple Kafka producer that sends a message every second to the Kafka topic "hello-world" via the channel "hello-world-in" with a sequential ID and an identifier from the PRODUCER_ID environment variable.

@ApplicationScoped
class HelloWorldProducer(
    @Channel("hello-world-out") emitter: Emitter[String]
  ):
  // Store a sequential ID for the messages
  private var id = 0
  @Transactional
  @Scheduled(every = "1s", delay = 5, delayUnit = TimeUnit.SECONDS, identity = "hello-world-producer")
  @Counted(name = "producedHelloMessages", description = "How many hello messages were produced")
  def produce() =
    val producerId = scala.util.Properties.envOrElse("PRODUCER_ID", "unknown")
    val message    = s"Hello World from $producerId - $id"
    Log.info("Producing a message: " + message)
    emitter.send(message)
    id += 1
end HelloWorldProducer
