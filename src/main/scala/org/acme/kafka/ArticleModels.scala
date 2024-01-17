package org.acme.kafka

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

enum ArticleStatus:
    case New, Processed

case class Article(
    id:     String,
    title:  String,
    status: ArticleStatus = ArticleStatus.New,
  ):
    // Override toString to print the article in a readable format
    override def toString = s"Article(id=$id, title=$title, status=$status)"

class ArticleDeserializer extends ObjectMapperDeserializer[Article](classOf[Article])
