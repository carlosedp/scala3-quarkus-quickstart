package org.acme.persistence.magnum

import com.augustnagro.magnum.DbCodec

case class UserCreateUpdateCommand(
    name:           String,
    email:          String,
    hashedPassword: String,
  ) derives DbCodec
