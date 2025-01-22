package xyz.yamida.kafka.client.api

import org.apache.kafka.clients.consumer.KafkaConsumer

interface Consumer {
    val consumer: KafkaConsumer<String, String>

    fun consume(topic: String, callback: (key: String?, value: String) -> Unit)
    fun close()
}
