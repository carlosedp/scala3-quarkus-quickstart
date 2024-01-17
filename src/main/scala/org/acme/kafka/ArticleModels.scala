package org.acme.kafka

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

case class Article(
    id:        String,
    title:     String,
    processed: Boolean = false,
  ):
    // Override toString to print the article in a readable format
    override def toString = s"Article(id=$id, title=$title, processed=$processed)"

class ArticleDeserializer extends ObjectMapperDeserializer[Article](classOf[Article])
