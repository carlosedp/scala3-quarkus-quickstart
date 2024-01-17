package org.acme.kafka

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.reactive.messaging.memory.InMemoryConnector
import org.awaitility.Awaitility.await
import org.eclipse.microprofile.reactive.messaging.spi.Connector
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(classOf[KafkaResourceLivecycleManager])
class ArticleProcessorTest(@Connector("smallrye-in-memory") connector: InMemoryConnector):

    @Test
    def testArticleProcessor(): Unit =
        val articles          = connector.source[Article]("articles-in")
        val processedArticles = connector.sink[Article]("articles-out")
        val article           = Article("1", "Test article")
        articles.send(article)
        await().until(() => processedArticles.received().size() == 1)
        val processedArticle = processedArticles.received().get(0).getPayload()
        assertEquals(processedArticle, article.copy(status = ArticleStatus.Processed))
