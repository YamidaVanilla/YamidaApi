package xyz.yamida.jda.commander.api.option

import xyz.yamida.jda.commander.api.option.builder.CommandOption

/**
 * A base class for managing command options in a reusable manner.
 */
abstract class BaseCommandOptions : CommandOptions {
    private val options = mutableListOf<CommandOption<*>>()

    /**
     * Registers a [CommandOption] and adds it to the internal options list.
     *
     * @param T The type of the option's value.
     * @param option The [CommandOption] to register.
     * @return The registered [CommandOption].
     */
    protected fun <T> registerOption(option: CommandOption<T>): CommandOption<T> {
        options.add(option)
        return option
    }

    /**
     * Extension function to register a [CommandOption] and add it to the internal options list.
     *
     * @return The registered [CommandOption].
     */
    fun <T> CommandOption<T>.register(): CommandOption<T> {
        options.add(this)
        return this
    }

    /**
     * Retrieves all registered options.
     *
     * @return A list of all registered [CommandOption]s.
     */
    override fun getOptions(): List<CommandOption<*>> = options
}