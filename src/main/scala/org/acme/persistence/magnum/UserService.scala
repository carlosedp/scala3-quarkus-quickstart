package org.acme.persistence.magnum

import com.augustnagro.magnum.*
import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UserService(private val userRepo: UserRepository, private val ds: AgroalDataSource):
  // Use Quarkus' @Transactional instead of Magnum's transact(ds).
  // This should integrate better with the rest of the ecosystem,
  // for example in tests.

  @Transactional
  def create(user: UserCreateUpdateCommand): User =
    connect(ds):
      userRepo.insertReturning(user)

  @Transactional
  def getAll: Vector[User] =
    connect(ds):
      userRepo.findAll

  @Transactional
  def getById(id: Long): Option[User] =
    connect(ds):
      userRepo.findById(id)

  @Transactional
  def updateUser(id: Long, user: UserCreateUpdateCommand): Option[User] =
    connect(ds):
      for
        existing <- userRepo.findById(id)
        updated = existing.copy(name = user.name, email = user.email)
      yield
        userRepo.update(updated)
        updated

  def deleteUser(id: Long): Unit =
    connect(ds):
      userRepo.deleteById(id)
end UserService
