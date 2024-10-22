package org.acme.persistence.hibernate

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional

@ApplicationScoped
class TaskService(private val em: EntityManager):

  @Transactional
  def create(task: Task): Task =
    em.persist(task)
    task

  @Transactional
  def getAll: Array[Task] =
    em.createQuery("SELECT t FROM Task t", classOf[Task]).getResultList().toArray(Array.empty[Task])
