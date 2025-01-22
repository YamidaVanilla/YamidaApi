package xyz.yamida.kafka.client.api

import org.apache.kafka.clients.producer.KafkaProducer

interface Producer {
    val producer: KafkaProducer<String, String>

    fun send(topic: String, key: String, value: String)
    fun close()
}
