package org.acme.persistence.magnum

import jakarta.ws.rs.*

@Path("/users")
class UserResource(private val userService: UserService):

    @POST
    def createUser(userCreateCommand: UserCreateUpdateCommand): User =
        userService.create(userCreateCommand)

    @GET
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

end UserResource
