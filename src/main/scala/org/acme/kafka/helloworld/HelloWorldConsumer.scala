package org.acme.kafka.helloworld

import io.quarkus.logging.Log

import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.reactive.messaging.Incoming

// This is a simple Kafka consumer that reads messages from the "hello-world-out" channel and logs them.

@ApplicationScoped
class HelloWorldConsumer:
  @Incoming("hello-world-in")
  @Blocking
  @Counted(name = "consumedHelloMessages", description = "How many hello messages were consumed")
  def consumeMessage(msg: String): Unit =
    Log.info(s"Received message: ${msg}")
