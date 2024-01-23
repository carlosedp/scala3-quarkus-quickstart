package org.acme.persistence.magnum

import java.util

case class UserPageData(
    title: String,
    users: util.List[User],
  )
