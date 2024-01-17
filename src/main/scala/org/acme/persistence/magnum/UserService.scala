package org.acme.persistence.magnum

import com.augustnagro.magnum.*
import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UserService(private val userRepo: UserRepository, private val ds: AgroalDataSource):

    def create(user: UserCreateUpdateCommand): User =
        transact(ds):
            userRepo.insertReturning(user)

    def getAll: Vector[User] =
        transact(ds):
            userRepo.findAll

    // Use Quarkus' @Transactional instead of Magnum's transact(ds).
    // This should integrate better with the rest of the ecosystem,
    // for example in tests.
    @Transactional
    def updateUser(id: Long, user: UserCreateUpdateCommand): Option[User] =
        connect(ds):
            for
                existing <- userRepo.findById(id)
                updated = existing.copy(name = user.name, email = user.email)
            yield
                userRepo.update(updated)
                updated
end UserService
