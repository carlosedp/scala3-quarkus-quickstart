package org.acme.persistence.hibernate

import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.{Assertions, Test}

@QuarkusTest
class TaskServiceTest(private val taskService: TaskService):

    // The following tests show that they are isolated when we use the @TestTransaction annotation
    @Test
    @TestTransaction
    def testCreateTaskIsolated1: Unit =
        val task: Task = makeTestTask
        taskService.create(task)
        val tasks = taskService.getAll
        Assertions.assertTrue(tasks.length == 1)

    @Test
    @TestTransaction
    def testCreateTaskIsolated2: Unit =
        val task: Task = makeTestTask
        taskService.create(task)
        val tasks = taskService.getAll
        Assertions.assertTrue(tasks.length == 1)

    private def makeTestTask =
        val task = new Task()
        task.title = "test"
        task.description = "test description"
        task

end TaskServiceTest
