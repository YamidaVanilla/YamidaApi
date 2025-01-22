package xyz.yamida.kafka.client.api

interface Client {
    val producer: Producer
    val consumer: Consumer?

    fun send(topic: String, key: String, value: String)
    fun consume(topic: String, callback: (key: String?, value: String) -> Unit)
    fun close()
}