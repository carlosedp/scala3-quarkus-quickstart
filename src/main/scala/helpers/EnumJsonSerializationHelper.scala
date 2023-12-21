package helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.pjfanning.`enum`.EnumModule
import io.quarkus.jackson.ObjectMapperCustomizer

import jakarta.inject.Singleton

@Singleton
class Scala3ObjectMapperCustomizer extends ObjectMapperCustomizer:
    def customize(mapper: ObjectMapper) =
        // General Scala support
        // https://github.com/FasterXML/jackson-module-scala
        mapper.registerModule(DefaultScalaModule)
        // Suport for Scala 3 Enums
        // https://github.com/pjfanning/jackson-module-scala3-enum
        mapper.registerModule(EnumModule)
