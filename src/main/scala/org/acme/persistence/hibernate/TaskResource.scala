package org.acme.persistence.hibernate

import io.quarkus.qute.{Template, TemplateInstance}
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.*

import scala.jdk.CollectionConverters.*

@Path("/tasks")
class TaskResource(
    private val tasksPage:   Template,
    private val taskService: TaskService,
  ):

    @GET
    @Produces(Array("application/json"))
    def getAll: List[TaskViewModel] =
        taskService.getAll.asScala.map(TaskViewModel(_)).toList

    @POST
    @Consumes(Array("application/json"))
    @Produces(Array("application/json"))
    def create(taskCreateCommand: TaskCreateCommand): TaskViewModel =
        TaskViewModel(taskService.create(Task(taskCreateCommand)))

    @GET
    @Path("/tasks-page")
    @Produces(Array("text/html"))
    @Blocking
    def showTasksPage(): TemplateInstance =
        tasksPage.data("page", TaskPageData("Tasks", taskService.getAll))

    @POST
    @Path("/tasks-page")
    @Produces(Array("text/html"))
    @Consumes(Array("application/x-www-form-urlencoded"))
    @Blocking
    def addTask(
        @FormParam("title") title:             String,
        @FormParam("description") description: String,
      ): TemplateInstance =
        taskService.create(Task(TaskCreateCommand(title, description)))

        tasksPage.data("page", TaskPageData("Tasks", taskService.getAll))

end TaskResource
