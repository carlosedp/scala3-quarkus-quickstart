package org.acme

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.fasterxml.jackson.annotation.{JsonProperty, JsonValue}

import jakarta.inject.Inject

enum AnEnum:
    case A extends AnEnum
    case B extends AnEnum

case class Other(@JsonProperty("foo") foo: String)

case class Something(
    @JsonProperty("name") name:         String,
    @JsonProperty("someEnum") someEnum: AnEnum,
    @JsonValue other:                   Other,
  )

@QuarkusTest
class Scala3ObjectMapperCustomizerTest:

    @Inject
    var objectMapper: ObjectMapper = null

    @Test
    def parseJsonToScalaObject =
        val sampleSomethingJSON = """
                                    |{
                                    |"name": "My Something",
                                    |"someEnum": "A",
                                    |"other": {
                                    |    "foo": "bar"
                                    |  }
                                    |}
                                    |""".stripMargin

        val parsed = objectMapper.readValue[Something](sampleSomethingJSON, classOf[Something])
        assertEquals(parsed.name, "My Something")
        assertEquals(parsed.someEnum, AnEnum.A)
        assertEquals(parsed.other.foo, "bar")

    @Test
    def generateJsonFromScalaObject =
        val something = Something("My Something", AnEnum.A, Other("bar"))
        val json      = objectMapper.writeValueAsString(something)
        assertEquals(json, """{"name":"My Something","someEnum":"A","other":{"foo":"bar"}}""")

end Scala3ObjectMapperCustomizerTest
