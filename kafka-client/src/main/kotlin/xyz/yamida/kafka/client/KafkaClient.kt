package xyz.yamida.kafka.client

import xyz.yamida.kafka.client.api.Client
import xyz.yamida.kafka.client.api.Consumer
import xyz.yamida.kafka.client.api.Producer

class KafkaClient(override val producer: Producer, override val consumer: Consumer? = null) : Client {

    override fun send(topic: String, key: String, value: String) {
        producer.send(topic, key, value)
    }

    override fun consume(topic: String, callback: (key: String?, value: String) -> Unit) {
        consumer?.consume(topic, callback) ?: throw IllegalStateException("Consumer is not initialized.")
    }

    override fun close() {
        producer.close()
        consumer?.close()
    }
}
