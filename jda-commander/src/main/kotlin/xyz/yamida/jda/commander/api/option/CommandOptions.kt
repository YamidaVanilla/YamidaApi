package xyz.yamida.jda.commander.api.option

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import xyz.yamida.jda.commander.api.option.builder.CommandOption

/**
 * An interface defining the behavior for managing and retrieving command options.
 */
interface CommandOptions {
    /**
     * Retrieves all options associated with the command.
     *
     * @return A list of all registered [CommandOption]s.
     */
    fun getOptions(): List<CommandOption<*>>

    /**
     * Retrieves the value of a specific [CommandOption] from a [SlashCommandInteractionEvent].
     *
     * @param T The type of the option's value.
     * @param event The [SlashCommandInteractionEvent] containing the command's input.
     * @param option The [CommandOption] whose value should be retrieved.
     * @return The extracted value of the option.
     */
    fun <T> getOptionValue(event: SlashCommandInteractionEvent, option: CommandOption<T>): T
}