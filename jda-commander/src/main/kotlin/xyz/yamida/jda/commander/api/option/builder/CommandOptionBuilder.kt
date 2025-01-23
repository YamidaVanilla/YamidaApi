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
class CommandOptionBuilder<T>(
    val type: OptionType,
    val configure: CommandOptionBuilder<T>.() -> Unit
) {
    lateinit var name: String         // The name of the option.
    lateinit var description: String  // The description of the option.
    var optional: Boolean = false     // Whether the option is optional.
    var mapper: (SlashCommandInteractionEvent) -> T = { throw IllegalStateException("Mapper not defined") }

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