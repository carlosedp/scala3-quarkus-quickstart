package org.acme.persistence.hibernate

import scala.compiletime.uninitialized

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class Task:
    @Id @GeneratedValue(GenerationType.IDENTITY) var id: Long    = uninitialized
    var title:                                           String  = uninitialized
    var description:                                     String  = uninitialized
    var done:                                            Boolean = uninitialized

object Task:
    def apply(command: TaskCreateCommand): Task =
        val task = new Task

        task.title = command.title
        task.description = command.description
        task.done = false
        task
