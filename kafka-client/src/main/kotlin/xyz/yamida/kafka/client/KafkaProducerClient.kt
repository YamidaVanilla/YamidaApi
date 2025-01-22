package xyz.yamida.xyz.yamida.kafka.client

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import xyz.yamida.kafka.client.api.Producer
import java.util.*

class KafkaProducerClient(brokers: String) : Producer {
    override val producer = KafkaProducer<String, String>(Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    })

    override fun send(topic: String, key: String, value: String) {
        try {
            producer.send(ProducerRecord(topic, key, value)).get()
            println("Message sent to topic $topic with key $key.")
        } catch (e: Exception) {
            println("Error sending message: ${e.message}")
        }
    }

    override fun close() {
        producer.close()
    }
}