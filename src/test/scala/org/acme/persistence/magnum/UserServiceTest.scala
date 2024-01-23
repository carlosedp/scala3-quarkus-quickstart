package org.acme.persistence.magnum

import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.{Assertions, Test}

@QuarkusTest
class UserServiceTest(private val userService: UserService):

    private def makeTestUser =
        UserCreateUpdateCommand(
            name = "test",
            email = "test@example.com",
            hashedPassword = "test",
        )

    @Test
    @TestTransaction
    def shouldFindAllUsers(): Unit =
        userService.create(makeTestUser)
        userService.create(makeTestUser)

        val allUsers = userService.getAll

        Assertions.assertTrue(allUsers.size == 2)

    @Test
    @TestTransaction
    def shouldFindAllUsers2(): Unit =
        userService.create(makeTestUser)
        userService.create(makeTestUser)

        val allUsers = userService.getAll

        Assertions.assertTrue(allUsers.size == 2)

    @Test
    @TestTransaction
    def userUpdateTest(): Unit =
        val user        = userService.create(makeTestUser)
        val updatedUser = userService.updateUser(user.id, makeTestUser.copy(name = "updated"))

        Assertions.assertTrue(updatedUser.exists(_.name == "updated"))

    @Test
    @TestTransaction
    def userDeleteTest(): Unit =
        val user = userService.create(makeTestUser)
        userService.deleteUser(user.id)

        Assertions.assertTrue(userService.getAll.isEmpty)

end UserServiceTest
