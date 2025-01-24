package xyz.yamida.jda.commander

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import xyz.yamida.jda.commander.api.CommandHandler
import xyz.yamida.jda.commander.api.option.CommandOptions

/**
 * Abstract base class for defining a Slash command. Provides default logic for registration and execution.
 */
abstract class SlashCommand : CommandHandler {
    /**
     * The name of the command, as it will appear in Discord.
     */
    abstract val name: String

    /**
     * A short description of the command's functionality.
     */
    abstract val description: String

    /**
     * A option handler.
     */
    open val options: CommandOptions? = null

    /**
     * Checks whether the user has permission to execute this command.
     * By default, all users have permission.
     *
     * @param event The [SlashCommandInteractionEvent] containing the command data.
     * @return True if the user has permission, false otherwise.
     */
    open fun hasPermission(event: SlashCommandInteractionEvent): Boolean = true

    /**
     * Executes the command logic. Must be implemented by subclasses.
     *
     * @param event The [SlashCommandInteractionEvent] containing the command data.
     */
    abstract fun execute(event: SlashCommandInteractionEvent)

    /**
     * Registers this command by building its [CommandData] representation.
     *
     * @return A list containing the [CommandData] for this command.
     */
    override fun registerCommands(): List<CommandData> {
        val command = Commands.slash(name, description).apply {
            this@SlashCommand.options?.getOptions()?.forEach { addOptions(it.toOptionData()) }
        }
        return listOf(command)
    }

    /**
     * Handles the execution of this command when triggered by a Slash command event.
     *
     * @param event The [SlashCommandInteractionEvent] containing the command data.
     * @return True if the command was executed successfully, false otherwise.
     */
    override fun handleCommand(event: SlashCommandInteractionEvent): Boolean {
        if (event.name != name) return false

        if (!hasPermission(event)) {
            val embed = EmbedBuilder()
                .setTitle("Ошибка")
                .setDescription("У вас недостаточно прав для использования этой команды.")
                .setColor(0xFF0000)
                .build()

            event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue()
            return true
        }

        execute(event)
        return true
    }
}