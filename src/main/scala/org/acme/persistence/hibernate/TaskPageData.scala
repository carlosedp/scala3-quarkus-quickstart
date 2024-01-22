package org.acme.persistence.hibernate

import java.util

case class TaskPageData(
    title: String,
    tasks: util.List[Task],
  )
