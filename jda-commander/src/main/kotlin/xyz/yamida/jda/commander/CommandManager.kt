package xyz.yamida.jda.commander

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import xyz.yamida.jda.commander.api.CommandHandler

/**
 * Manages the lifecycle and execution of Slash commands across multiple handlers.
 *
 * @param handlers A list of [CommandHandler] instances responsible for handling commands.
 */
class CommandManager(val handlers: List<CommandHandler>) : ListenerAdapter() {

    /**
     * Triggered when a guild is ready. Registers all commands from the provided handlers.
     *
     * @param event The [GuildReadyEvent] triggered when a guild becomes available.
     */
    override fun onGuildReady(event: GuildReadyEvent) {
        handlers.forEach { handler ->
            val commands = handler.registerCommands()
            if (commands.isNotEmpty()) {
                event.guild.updateCommands().addCommands(commands).queue()
                println("Registered commands for guild ${event.guild.name}")
            }
        }
    }

    /**
     * Triggered when a Slash command interaction occurs. Delegates execution to the appropriate handler.
     *
     * @param event The [SlashCommandInteractionEvent] containing the command interaction data.
     */
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        for (handler in handlers) {
            if (handler.handleCommand(event)) {
                return
            }
        }

        val embed = EmbedBuilder()
            .setTitle("Ошибка")
            .setDescription("Команда не найдена.")
            .setColor(0xFF0000)
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}
