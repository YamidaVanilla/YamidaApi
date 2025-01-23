package xyz.yamida.jda.commander.api.option

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
}