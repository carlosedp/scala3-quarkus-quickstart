package org.acme.persistence.magnum

import jakarta.ws.rs.{GET, POST, Path}

@Path("/users")
class UserResource(private val userService: UserService):

    @POST
    def createUser(userCreateCommand: UserCreateCommand): User =
        userService.create(userCreateCommand)

    @GET
    def getUsers: Vector[User] =
        userService.getAll
