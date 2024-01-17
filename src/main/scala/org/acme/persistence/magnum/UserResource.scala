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
    @Consumes(Array("application/json"))
    @Produces(Array("application/json"))
    def updateUser(@PathParam("id") id: Long, user: UserCreateUpdateCommand): User =
        userService
            .updateUser(id, user)
            .getOrElse(throw NotFoundException("User not found"))
end UserResource
