package org.acme

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import jakarta.inject.Inject

enum AnEnum:
    case A extends AnEnum
    case B extends AnEnum

case class Other(foo: String)
case class Something(
    name:     String,
    someEnum: AnEnum,
    other:    Other,
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
    end parseJsonToScalaObject

    @Test
    def generateJsonFromScalaObject =
        val something = Something("My Something", AnEnum.A, Other("bar"))
        val json      = objectMapper.writeValueAsString(something)
        assertEquals(json, """{"name":"My Something","someEnum":"A","other":{"foo":"bar"}}""")

end Scala3ObjectMapperCustomizerTest
