package org.acme

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.*
import org.eclipse.microprofile.config.ConfigProvider
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.metrics.annotation.Counted
import org.jboss.resteasy.reactive.RestQuery

@Path("/")
class GreetingResource(
    // Let's inject the configuration property "greeting.message" into a variable
    @ConfigProperty(name = "greeting.message") message: String
  ):
    @GET
    @Path("/hello")
    @Counted(name = "helloCounter", description = "How many times the hello endpoint was invoked")
    @Produces(Array(TEXT_PLAIN))
    def hello() =
        // Or programatically access the configuration property greeting.suffix
        val messageSuffix = ConfigProvider.getConfig().getValue("greeting.suffix", classOf[String])
        s"Hello ${message} from RESTEasy Reactive in Scala 3${messageSuffix}"

    // This is an endpoint which greets a name or names passed via a query parameter "name"
    @GET
    @Path("/greet")
    @Produces(Array(TEXT_PLAIN))
    def greeting(@RestQuery @DefaultValue("world") name: Array[String]) =
        val names = name.mkString(" and ")
        s"Hello ${names} from RESTEasy Reactive in Scala 3"

    // Generates a greeting message in JSON format
    @GET
    @Path("/greet/json")
    @Produces(Array(APPLICATION_JSON))
    def greetingJson(@RestQuery @DefaultValue("world") name: Array[String]) =
        val names = name.mkString(" and ")
        s"""{"message": "Hello ${names} from RESTEasy Reactive in Scala 3"}"""

end GreetingResource
