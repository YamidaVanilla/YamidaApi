package xyz.megadev.mafia.server.components

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

interface MessagingListener : Component, PluginMessageListener {
    val channel: String

    override fun onPluginMessageReceived(id: String, player: Player, message: ByteArray) {
        if (id != channel) {
            return
        }

        onPluginMessageReceived(player, message)
    }

    fun onPluginMessageReceived(player: Player, message: ByteArray)
}