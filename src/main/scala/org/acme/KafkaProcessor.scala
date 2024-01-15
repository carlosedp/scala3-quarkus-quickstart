package org.acme

import io.quarkus.logging.Log

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import io.smallrye.reactive.messaging.annotations.Blocking

@ApplicationScoped
class ArticleProcessor:
    @Incoming("articles-in")
    @Outgoing("articles-out")
    @Blocking
    def processArticle(article: Article) =
        Log.info(s"Received article: ${article}")
        Thread.sleep(1500)
        val processedArticle = article.copy(processed = true)
        Log.info(s"Processed article: ${processedArticle}")
        processedArticle
