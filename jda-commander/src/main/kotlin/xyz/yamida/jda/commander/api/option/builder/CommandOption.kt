package xyz.yamida.jda.commander.api.option.builder

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

/**
 * Represents a command option for a Slash command in Discord.
 *
 * @param T The type of the value that this option represents.
 */
class CommandOption<T>(
    val name: String,
    val description: String,
    val type: OptionType,
    val required: Boolean = false,
    val mapper: (SlashCommandInteractionEvent) -> T,
    private val choices: List<Pair<String, String>> = emptyList()
) {
    /**
     * Retrieves the value of this option from the provided SlashCommandInteractionEvent.
     */
    fun get(event: SlashCommandInteractionEvent): T = mapper(event)

    /**
     * Converts this option to a [OptionData] object.
     */
    fun toOptionData(): OptionData {
        val optionData = OptionData(type, name, description).setRequired(required)
        choices.forEach { (choiceName, choiceValue) ->
            optionData.addChoice(choiceName, choiceValue)
        }
        return optionData
    }
}
