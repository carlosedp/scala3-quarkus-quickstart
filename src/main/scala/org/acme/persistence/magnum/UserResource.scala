package org.acme.persistence.magnum

import io.quarkus.qute.{Template, TemplateInstance}
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.*

@Path("/users")
class UserResource(
    private val userPage:    Template,
    private val userService: UserService,
  ):

    @POST
    @Consumes(Array(core.MediaType.APPLICATION_JSON))
    @Produces(Array(core.MediaType.APPLICATION_JSON))
    def createUser(userCreateCommand: UserCreateUpdateCommand): User =
        userService.create(userCreateCommand)

    @GET
    @Produces(Array(core.MediaType.APPLICATION_JSON))
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
    @Produces(Array(core.MediaType.TEXT_HTML))
    @Blocking
    def getUsersPage: TemplateInstance =
        userPage.data("page", UserPageData("Users", userService.getAll.toArray))

    @POST
    @Path("/users-page")
    @Consumes(Array(core.MediaType.APPLICATION_FORM_URLENCODED))
    @Produces(Array(core.MediaType.TEXT_HTML))
    @Blocking
    def createUserPage(
        @FormParam("name") name:         String,
        @FormParam("email") email:       String,
        @FormParam("password") password: String,
      ): TemplateInstance =
        userService.create(UserCreateUpdateCommand(name, email, password))

        userPage.data("page", UserPageData("Users", userService.getAll.toArray))

end UserResource
