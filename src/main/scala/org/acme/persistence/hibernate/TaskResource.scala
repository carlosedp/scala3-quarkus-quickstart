package org.acme.persistence.hibernate

import io.quarkus.qute.{Template, TemplateInstance}
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.*

@Path("/tasks")
class TaskResource(
    private val tasksPage:   Template,
    private val taskService: TaskService,
  ):

    @GET
    @Produces(Array(core.MediaType.APPLICATION_JSON))
    def getAll: List[TaskViewModel] =
        taskService.getAll.map(TaskViewModel(_)).toList

    @POST
    @Consumes(Array(core.MediaType.APPLICATION_JSON))
    @Produces(Array(core.MediaType.APPLICATION_JSON))
    def create(taskCreateCommand: TaskCreateCommand): TaskViewModel =
        TaskViewModel(taskService.create(Task(taskCreateCommand)))

    @GET
    @Path("/tasks-page")
    @Produces(Array(core.MediaType.TEXT_HTML))
    @Blocking
    def showTasksPage(): TemplateInstance =
        tasksPage.data("page", TaskPageData("Tasks", taskService.getAll))

    @POST
    @Path("/tasks-page")
    @Produces(Array(core.MediaType.TEXT_HTML))
    @Consumes(Array(core.MediaType.APPLICATION_FORM_URLENCODED))
    @Blocking
    def addTask(
        @FormParam("title") title:             String,
        @FormParam("description") description: String,
      ): TemplateInstance =
        taskService.create(Task(TaskCreateCommand(title, description)))

        tasksPage.data("page", TaskPageData("Tasks", taskService.getAll))

end TaskResource
