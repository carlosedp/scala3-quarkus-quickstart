package org.acme.kafka

import java.util.HashMap

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.smallrye.reactive.messaging.memory.InMemoryConnector

class KafkaResourceLivecycleManager extends QuarkusTestResourceLifecycleManager:
    @Override
    def start() =
        val env    = new HashMap[String, String]
        val props1 = InMemoryConnector.switchIncomingChannelsToInMemory("articles-in")
        val props2 = InMemoryConnector.switchOutgoingChannelsToInMemory("articles-out")
        val props3 = InMemoryConnector.switchIncomingChannelsToInMemory("articles-processed")
        val props4 = InMemoryConnector.switchOutgoingChannelsToInMemory("articles")
        env.putAll(props1)
        env.putAll(props2)
        env.putAll(props3)
        env.putAll(props4)
        env

    @Override
    def stop(): Unit =
        InMemoryConnector.clear()
