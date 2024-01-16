package org.acme.persistence.magnum

import com.augustnagro.magnum.*
import jakarta.enterprise.context.ApplicationScoped

@Table(PostgresDbType, SqlNameMapper.CamelToSnakeCase)
@SqlName("app_users")
case class User(
    @Id id:         Long,
    name:           String,
    email:          String,
    hashedPassword: String,
  ) derives DbCodec

@ApplicationScoped
class UserRepository extends Repo[UserCreateUpdateCommand, User, Long]
