package org.acme.persistence.hibernate

case class TaskPageData(
    title: String,
    tasks: Array[Task],
  )
