package xyz.yamida.wrapper.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener

abstract class KafkaConsumer<T>(
    val objectMapper: ObjectMapper,
    val topics: Array<String>,
    val groupId: String
) {

    abstract fun processMessage(message: T)

    @KafkaListener(topics = ["#{__listener.topics}"], groupId = "#{__listener.groupId}")
    fun consume(message: String) {
        try {
            val data = objectMapper.readValue(message, getMessageClass())
            processMessage(data)
        } catch (ex: Exception) {
            println("Error processing message: ${ex.message}")
        }
    }

    protected abstract fun getMessageClass(): Class<T>
}
