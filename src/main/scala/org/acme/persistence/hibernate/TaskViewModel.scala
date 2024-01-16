package org.acme.persistence.hibernate

case class TaskViewModel(
    id:          Long,
    title:       String,
    description: String,
    done:        Boolean,
  )

object TaskViewModel:
    def apply(task: Task): TaskViewModel =
        TaskViewModel(
            task.id,
            task.title,
            task.description,
            task.done,
        )
