package org.acme.persistence.magnum

import com.augustnagro.magnum.DbCodec

case class UserCreateCommand(
    name:           String,
    email:          String,
    hashedPassword: String,
  ) derives DbCodec
