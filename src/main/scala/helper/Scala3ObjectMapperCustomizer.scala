package helper

import io.quarkus.jackson.ObjectMapperCustomizer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import jakarta.inject.Singleton

@Singleton
class Scala3ObjectMapperCustomizer extends ObjectMapperCustomizer:
  def customize(mapper: ObjectMapper) =
    // General Scala support
    // https://github.com/FasterXML/jackson-module-scala
    mapper.registerModule(DefaultScalaModule)
