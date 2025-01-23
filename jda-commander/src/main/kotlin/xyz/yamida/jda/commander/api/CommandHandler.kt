package xyz.yamida.jda.commander.api

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

/**
 * Interface defining the core logic for custom command handling in Discord bots.
 */
interface CommandHandler {
    /**
     * Registers the commands associated with this handler.
     *
     * @return A list of [CommandData] representing the commands to be registered.
     */
    fun registerCommands(): List<CommandData>

    /**
     * Handles the execution of a command when triggered by a Slash command event.
     *
     * @param event The [SlashCommandInteractionEvent] that triggered the command.
     * @return True if the command was handled, false otherwise.
     */
    fun handleCommand(event: SlashCommandInteractionEvent): Boolean
}
