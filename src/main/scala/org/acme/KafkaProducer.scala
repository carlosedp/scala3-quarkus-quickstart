package org.acme

import java.util.UUID;
import jakarta.ws.rs.{GET, POST, Path, Produces, core}
import org.jboss.resteasy.reactive.RestForm

import org.eclipse.microprofile.reactive.messaging.Channel
import io.smallrye.reactive.messaging.MutinyEmitter

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

case class Article(
    id:        String,
    title:     String,
    processed: Boolean = false,
  )

class ArticleDeserializer extends ObjectMapperDeserializer[Article](classOf[Article])

@Path("/article")
class ArticleProducer(@Channel("requests-out") emitter: MutinyEmitter[Article]):
    // Create an HTML page with a form to submit a new article
    @GET
    @Produces(Array(core.MediaType.TEXT_HTML))
    def article() =
        val uuid = UUID.randomUUID().toString()
        s"""
           |<html>
           |  <head>
           |    <title>Submit an article</title>
           |  </head>
           |  <body>
           |    <form action="/article" method="post">
           |      <label for="id">ID:</label><br>
           |      <input type="text" id="id" name="id" value=$uuid size=40><br>
           |      <label for="title">Title:</label><br>
           |      <input type="text" id="title" name="title" size=50><br><br>
           |      <input type="submit" value="Submit">
           |    </form>
           |  </body>
           |</html>
           |""".stripMargin
    end article

    // Endpoint to submit a new article
    @POST
    @Produces(Array(core.MediaType.TEXT_HTML))
    def articlePost(@RestForm id: String, @RestForm title: String): io.smallrye.mutiny.Uni[String] =
        // Publish the article to the Kafka topic
        emitter.send(Article(id, title))
            // Return a HTML page to confirm the article was submitted
            .map(_ => s"""
                         |<html>
                         |  <head>
                         |    <title>Article submitted</title>
                         |  </head>
                         |  <body>
                         |    <p>Article submitted with ID: ${id} and title: ${title}</p>
                         |  </body>
                         |</html>
                         |""".stripMargin)
            // If the Kafka topic is not available, return an error message
            .onFailure().recoverWithItem("Error: Kafka topic not available")

end ArticleProducer
