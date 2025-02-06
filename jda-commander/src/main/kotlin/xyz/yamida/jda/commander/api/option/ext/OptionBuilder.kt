package xyz.yamida.jda.commander.api.option.ext

import net.dv8tion.jda.api.interactions.commands.OptionType
import xyz.yamida.jda.commander.api.option.BaseCommandOptions
import xyz.yamida.jda.commander.api.option.builder.CommandOption
import xyz.yamida.jda.commander.api.option.builder.CommandOptionBuilder

fun BaseCommandOptions.stringParam(configure: CommandOptionBuilder<String?>.() -> Unit): CommandOption<String?> {
    return CommandOptionBuilder(OptionType.STRING, configure).build().register()
}

fun BaseCommandOptions.intParam(configure: CommandOptionBuilder<Int?>.() -> Unit): CommandOption<Int?> {
    return CommandOptionBuilder(OptionType.INTEGER, configure).build().register()
}