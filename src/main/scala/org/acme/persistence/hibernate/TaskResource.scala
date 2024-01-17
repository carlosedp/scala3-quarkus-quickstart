package org.acme.persistence.hibernate

import jakarta.ws.rs.{Consumes, POST, Path, Produces}

@Path("/tasks")
class TaskResource(private val taskService: TaskService):

    @POST
    @Consumes(Array("application/json"))
    @Produces(Array("application/json"))
    def create(taskCreateCommand: TaskCreateCommand): TaskViewModel =
        TaskViewModel(taskService.create(Task(taskCreateCommand)))
