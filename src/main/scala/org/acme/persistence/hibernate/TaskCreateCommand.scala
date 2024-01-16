package org.acme.persistence.hibernate

case class TaskCreateCommand(
    title:       String,
    description: String,
  )
