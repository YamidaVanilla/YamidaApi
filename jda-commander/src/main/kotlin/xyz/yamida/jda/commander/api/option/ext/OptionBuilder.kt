package xyz.yamida.jda.commander.api.option.ext

import net.dv8tion.jda.api.interactions.commands.OptionType
import xyz.yamida.jda.commander.SlashCommand
import xyz.yamida.jda.commander.api.option.builder.CommandOption
import xyz.yamida.jda.commander.api.option.builder.CommandOptionBuilder

fun SlashCommand.stringParam(configure: CommandOptionBuilder<String>.() -> Unit): CommandOption<String> {
    return CommandOptionBuilder(OptionType.STRING, configure).build()
}

fun SlashCommand.intParam(configure: CommandOptionBuilder<Int>.() -> Unit): CommandOption<Int> {
    return CommandOptionBuilder(OptionType.INTEGER, configure).build()
}