package org.acme.kafka

import io.quarkus.logging.Log
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.{Incoming, Outgoing}

@ApplicationScoped
class ArticleProcessor:
    @Incoming("articles-in")
    @Outgoing("articles-out")
    @Blocking
    def processArticle(article: Article) =
        Log.info(s"Received article: ${article}")
        Thread.sleep(1500)
        val processedArticle = article.copy(status = ArticleStatus.Processed)
        Log.info(s"Processed article: ${processedArticle}")
        processedArticle
