package xyz.yamida.jda.commander.api.option.builder

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType

/**
 * A builder class for constructing [CommandOption] instances with a customizable configuration.
 *
 * @param T The type of the value that this option represents.
 * @property type The type of the option (e.g., STRING, INTEGER).
 * @property configure A lambda function to define the properties of the option.
 */
open class CommandOptionBuilder<T>(
    val type: OptionType,
    val configure: CommandOptionBuilder<T>.() -> Unit
) {
    lateinit var name: String
    lateinit var description: String
    var optional: Boolean = false
    private val choices = mutableListOf<Pair<String, String>>()

    var mapper: (SlashCommandInteractionEvent) -> T = ::defaultMapper

    /**
     * Default mapper that extracts values based on option type.
     */
    open fun defaultMapper(event: SlashCommandInteractionEvent): T {
        return when (type) {
            OptionType.STRING -> event.getOption(name)?.asString as T
            OptionType.INTEGER -> event.getOption(name)?.asInt as T
            OptionType.BOOLEAN -> event.getOption(name)?.asBoolean as T
            else -> throw IllegalStateException("Unsupported option type: $type")
        }
    }

    /**
     * Adds multiple choices through a block-based configuration.
     */
    fun choice(configure: ChoiceBuilder.() -> Unit) {
        val builder = ChoiceBuilder()
        builder.configure()
        choices.addAll(builder.choices)
    }

    /**
     * Constructs and returns a [CommandOption] instance based on the provided configuration.
     *
     * @return A configured [CommandOption] instance.
     */
    fun build(): CommandOption<T> {
        configure()
        return CommandOption(name, description, type, !optional, mapper, choices)
    }

    /**
     * A builder class for defining choices.
     */
    inner class ChoiceBuilder {
        val choices = mutableListOf<Pair<String, String>>()

        /**
         * Adds a string choice.
         *
         * @param value The value of the choice.
         */
        fun string(value: String) {
            choices.add(value to value)
        }

        /**
         * Adds an integer choice.
         *
         * @param value The value of the choice.
         */
        fun integer(value: Int) {
            if (type != OptionType.INTEGER) {
                throw IllegalArgumentException("Integer choices can only be added to INTEGER option types.")
            }
            choices.add(value.toString() to value.toString())
        }
    }
}