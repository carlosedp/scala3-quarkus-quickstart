package org.acme.persistence.magnum

import io.agroal.api.AgroalDataSource
import com.augustnagro.magnum.*
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserService(private val userRepo: UserRepository, private val ds: AgroalDataSource):

    def create(user: UserCreateCommand): User =
        transact(ds):
            userRepo.insertReturning(user)

    def getAll: Vector[User] =
        transact(ds):
            userRepo.findAll
