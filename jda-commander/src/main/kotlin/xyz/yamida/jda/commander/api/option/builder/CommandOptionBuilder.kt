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

    var mapper: (SlashCommandInteractionEvent) -> T = ::defaultMapper

    /**
     * Default mapper that extracts values based on option type.
     * If the option is STRING, it gets the string value.
     * If the option is INTEGER, it gets the integer value.
     * If the option is BOOLEAN, it gets the boolean value.
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
     * Sets the mapper function to extract the option value from a [SlashCommandInteractionEvent].
     *
     * @param mapper A function defining how to extract the value of this option from the event.
     */
    fun mapper(mapper: (SlashCommandInteractionEvent) -> T) {
        this.mapper = mapper
    }

    /**
     * Constructs and returns a [CommandOption] instance based on the provided configuration.
     *
     * @return A configured [CommandOption] instance.
     */
    fun build(): CommandOption<T> {
        configure()
        return CommandOption(name, description, type, !optional, mapper)
    }
}