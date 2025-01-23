package xyz.yamida.jda.commander.api.option.builder

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

/**
 * Represents a command option for a Slash command in Discord.
 *
 * @param T The type of the value that this option represents.
 * @property name The name of the option, as it appears in the command.
 * @property description A brief description of the option, visible to users.
 * @property type The type of the option (e.g., STRING, INTEGER).
 * @property required Whether the option is mandatory.
 * @property mapper A function to extract the value of the option from a [SlashCommandInteractionEvent].
 */
class CommandOption<T>(
    val name: String,
    val description: String,
    val type: OptionType,
    val required: Boolean = false,
    val mapper: (SlashCommandInteractionEvent) -> T
) {
    /**
     * Retrieves the value of this option from the provided SlashCommandInteractionEvent.
     *
     * @return The value extracted from the event.
     */

    fun get(event: SlashCommandInteractionEvent): T = mapper(event)

    /**
     * Converts this option to a [OptionData] object, which is required for registering
     * commands with JDA.
     *
     * @return An [OptionData] instance representing this command option.
     */
    fun toOptionData(): OptionData {
        return OptionData(type, name, description).setRequired(required)
    }
}
