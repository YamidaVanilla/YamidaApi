package xyz.yamida.jda.commander.api.option

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import xyz.yamida.jda.commander.api.option.builder.CommandOption

/**
 * A base class for managing command options in a reusable manner.
 */
abstract class BaseCommandOptions : CommandOptions {
    private val optionsList = mutableListOf<CommandOption<*>>() // List of all registered options.

    /**
     * Registers a [CommandOption] and adds it to the internal options list.
     *
     * @param T The type of the option's value.
     * @param option The [CommandOption] to register.
     * @return The registered [CommandOption].
     */
    protected fun <T> registerOption(option: CommandOption<T>): CommandOption<T> {
        optionsList.add(option)
        return option
    }

    /**
     * Extension function to register a [CommandOption] and add it to the internal options list.
     *
     * @return The registered [CommandOption].
     */
    fun <T> CommandOption<T>.register(): CommandOption<T> {
        optionsList.add(this)
        return this
    }

    /**
     * Retrieves all registered options.
     *
     * @return A list of all registered [CommandOption]s.
     */
    override fun getOptions(): List<CommandOption<*>> = optionsList

    /**
     * Retrieves the value of a specific [CommandOption] from a [SlashCommandInteractionEvent].
     *
     * @param T The type of the option's value.
     * @param event The [SlashCommandInteractionEvent] containing the command's input.
     * @param option The [CommandOption] whose value should be retrieved.
     * @return The extracted value of the option.
     */
    override fun <T> getOptionValue(event: SlashCommandInteractionEvent, option: CommandOption<T>): T {
        return option.getValue(event)
    }
}