
# Yamida API

## Описание

Yamida API предоставляет библиотеки для взаимодействия с Apache Kafka и создания Slash-команд в Discord.
Библиотеки включают:

- **Kafka API** — модуль для работы с Apache Kafka, включая продюсера, консюмера и клиентский интерфейс.
- **JDA Command API** — модуль для создания и управления Slash-командами в Discord с использованием JDA.

## Kafka API

### Основные классы

#### KafkaClient

Класс предоставляет интерфейс для отправки и получения сообщений из Kafka.

**Пример использования**

```kotlin
val producer = KafkaProducerClient("localhost:9092")
val consumer = KafkaConsumerClient("localhost:9092", "test-group")
val client = KafkaClient(producer, consumer)

// Отправка сообщения
client.send("test-topic", "key1", "Hello, Kafka!")

// Чтение сообщений
client.consume("test-topic") { key, value ->
    println("Получено сообщение: key = $key, value = $value")
}

// Закрытие клиента
client.close()
```

#### KafkaProducerClient

Класс для отправки сообщений в Kafka.

**Пример использования**

```kotlin
val producer = KafkaProducerClient("localhost:9092")
producer.send("test-topic", "key1", "Hello, Kafka!")
producer.close()
```

#### KafkaConsumerClient

Класс для получения сообщений из Kafka.

**Пример использования**

```kotlin
val consumer = KafkaConsumerClient("localhost:9092", "test-group")
consumer.consume("test-topic") { key, value ->
    println("Получено сообщение: key = $key, value = $value")
}
consumer.close()
```

## JDA Command API

### Основные классы

#### SlashCommand

Абстрактный класс для создания Slash-команд в Discord.

**Пример реализации команды**

```kotlin
class PingCommand : SlashCommand() {
    override val name = "ping"
    override val description = "Ответит Pong!"

    override fun execute(event: SlashCommandInteractionEvent) {
        event.reply("Pong!").queue()
    }
}
```

#### CommandManager

Класс для управления и регистрации нескольких Slash-команд.

**Пример использования**

```kotlin
val pingCommand = PingCommand()
val commandManager = CommandManager(listOf(pingCommand))

jda.addEventListener(commandManager)
```

#### CommandOption

Класс для описания параметров команд.

**Пример использования**

```kotlin
class AdvancedCommand : SlashCommand() {
    override val name = "advanced"
    override val description = "Пример команды с параметрами."

    inner class Options : BaseCommandOptions() {
        val nickname = stringParam {
            name = "nickname"
            description = "Введите ваш игровой никнейм"
        }.register()

        val password = intParam {
            name = "password"
            description = "Введите ваш пароль (опционально)"
            optional = true
        }.register()
    }

    val container = Options()

    override val options: List<CommandOption<*>> = container.getOptions()

    override fun execute(event: SlashCommandInteractionEvent) {
        val nickname = container.nickname.get(event)
        val password = container.password.get(event)

        event.reply(
            "Вы ввели:\n" +
                    "Никнейм: $nickname\n" +
                    "Пароль: ${password}"
        ).queue()
    }
}
```

### Расширения для параметров

**Пример создания параметра через builder**

```kotlin
val command = object : SlashCommand() {
    override val name = "add"
    override val description = "Сложение двух чисел"

    inner class Options : BaseCommandOptions() {
        val nickname = stringParam {
            name = "nickname"
            description = "Введите ваш игровой никнейм"
        }.register()

        val password = intParam {
            name = "password"
            description = "Введите ваш пароль (опционально)"
            optional = true
        }.register()
    }

    val container = Options()

    override val options: List<CommandOption<*>> = container.getOptions()

    override fun execute(event: SlashCommandInteractionEvent) {
        val nickname = container.nickname.get(event)
        val password = container.password.get(event)
    }
}
```

## Установка

Добавьте зависимость для Kafka API в вашем проекте:

```kotlin
implementation "xyz.yamida:kafka-client:1.0.1"
```

Добавьте зависимость для JDA Command API:

```kotlin
implementation "xyz.yamida:jda-commander:1.0.4"
```

Настройте ваш JDA и подключите CommandManager.

## Лицензия

Данный проект распространяется под лицензией MIT.
