package xyz.yamida.xyz.yamida.kafka.client

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import xyz.yamida.kafka.client.api.Consumer
import java.time.Duration
import java.util.*

class KafkaConsumerClient(brokers: String, groupId: String) : Consumer {
    override val consumer = KafkaConsumer<String, String>(Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
        put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    })

    override fun consume(topic: String, callback: (key: String?, value: String) -> Unit) {
        consumer.subscribe(listOf(topic))
        try {
            while (true) {
                val records = consumer.poll(Duration.ofMillis(1000))
                records.forEach { record ->
                    callback(record.key(), record.value())
                }
            }
        } catch (e: InterruptedException) {
            println("Consumer interrupted: ${e.message}")
        } finally {
            close()
        }
    }

    override fun close() {
        consumer.close()
    }
}