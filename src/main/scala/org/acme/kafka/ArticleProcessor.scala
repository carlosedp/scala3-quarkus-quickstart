package org.acme.kafka

import io.quarkus.logging.Log
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.metrics.annotation.{Counted, Timed}
import org.eclipse.microprofile.reactive.messaging.{Incoming, Outgoing}

@ApplicationScoped
class ArticleProcessor:
    @Incoming("articles-in")
    @Outgoing("articles-out")
    @Blocking
    @Counted(name = "processedArticles", description = "How many articles were processed")
    @Timed(name = "processArticleTime", description = "A measure of how long it takes to process an article")
    def processArticle(article: Article): Article =
        Log.info(s"Received article: ${article}")
        val processingTime = 1500 + (math.random * 500).toInt
        Thread.sleep(processingTime)
        val processedArticle = article.copy(status = ArticleStatus.Processed)
        Log.info(s"Processed article: ${processedArticle} in ${processingTime}ms")
        processedArticle
