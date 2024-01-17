package org.acme.persistence.hibernate

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class Task:
    @Id @GeneratedValue(GenerationType.IDENTITY) var id: Long    = _
    var title:                                           String  = _
    var description:                                     String  = _
    var done:                                            Boolean = _

object Task:
    def apply(command: TaskCreateCommand): Task =
        val task = new Task

        task.title = command.title
        task.description = command.description
        task.done = false
        task
