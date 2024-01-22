package org.acme.persistence.magnum

import io.quarkus.qute.{Template, TemplateInstance}
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.*

import scala.jdk.CollectionConverters.*

@Path("/users")
class UserResource(
    private val userPage: Template,
    private val userService: UserService
  ):

    @POST
    @Consumes(Array("application/json"))
    @Produces(Array("application/json"))
    def createUser(userCreateCommand: UserCreateUpdateCommand): User =
        userService.create(userCreateCommand)

    @GET
    @Produces(Array("application/json"))
    def getUsers: Vector[User] =
        userService.getAll

    @PUT
    @Path("/{id}")
    @Consumes(Array(core.MediaType.APPLICATION_JSON))
    @Produces(Array(core.MediaType.APPLICATION_JSON))
    def updateUser(@PathParam("id") id: Long, user: UserCreateUpdateCommand): User =
        userService
            .updateUser(id, user)
            .getOrElse(throw NotFoundException("User not found"))

    @DELETE
    @Path("/{id}")
    @Consumes(Array(core.MediaType.APPLICATION_JSON))
    def deleteUser(@PathParam("id") id: Long): Unit =
        userService.deleteUser(id)


    @GET
    @Path("/users-page")
    @Produces(Array("text/html"))
    @Blocking
    def getUsersPage: TemplateInstance =
        userPage.data("page", UserPageData("Users", userService.getAll.asJava))

    @POST
    @Path("/users-page")
    @Consumes(Array("application/x-www-form-urlencoded"))
    @Produces(Array("text/html"))
    @Blocking
    def createUserPage(
        @FormParam("name") name:         String,
        @FormParam("email") email:       String,
        @FormParam("password") password: String,
      ): TemplateInstance =
        userService.create(UserCreateUpdateCommand(name, email, password))

        userPage.data("page", UserPageData("Users", userService.getAll.asJava))

end UserResource
