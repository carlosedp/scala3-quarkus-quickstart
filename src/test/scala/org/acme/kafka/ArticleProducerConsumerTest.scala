package org.acme.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.scala.extensions.*
import io.smallrye.reactive.messaging.memory.InMemoryConnector
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.awaitility.Awaitility.await
import org.eclipse.microprofile.reactive.messaging.spi.Connector
import org.hamcrest.CoreMatchers.{is, notNullValue}
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(classOf[KafkaResourceLivecycleManager])
class ArticleProducerConsumerTest(@Connector("smallrye-in-memory") connector: InMemoryConnector):

    @Inject
    var objectMapper: ObjectMapper = null

    @Test
    def testArticleString(): Unit =
        val article = Article("1", "Test article")
        assertEquals(article.toString(), "Article(id=1, title=Test article, status=New)")

    @Test
    def testArticleDeserializer(): Unit =
        val articleJson = """{"id":"1","title":"Test article", "status":"New"}"""
        val article     = ArticleDeserializer().deserialize("articles", articleJson.getBytes())
        assertEquals(article, Article("1", "Test article", ArticleStatus.New))

    @Test
    def testUUIDGenerator(): Unit =
        Given()
            .When(_.get("/article/uuid")).Then(res =>
                res.statusCode(200)
                res.body(is(notNullValue()))
            )

    @Test
    def testArticlePost(): Unit =
        // Create a new article
        val article     = Article("1", "Test article")
        val articleJson = objectMapper.writeValueAsString(article)

        Given()
            .When(_.body(articleJson).contentType(MediaType.APPLICATION_JSON).post("/article")).Then(res =>
                res.statusCode(200)
                res.body(is(article.id))
            )

    @Test
    def testPostedArticleToKafka(): Unit =
        // Create a new article
        val article     = Article("1", "Test article")
        val articleJson = objectMapper.writeValueAsString(article)

        Given()
            .When(_.body(articleJson).contentType(MediaType.APPLICATION_JSON).post("/article")).Then(res =>
                res.statusCode(200)
                res.body(is(article.id))
            )

        val articles = connector.sink[Article]("articles")
        await().until(() => articles.received().size() >= 1)
        val receivedArticle = articles.received().get(0).getPayload()
        assertEquals(receivedArticle, article)

end ArticleProducerConsumerTest
