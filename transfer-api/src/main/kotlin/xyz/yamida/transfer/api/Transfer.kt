package xyz.yamida.transfer.api

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


fun Player.transferToServer(server: String) {
    val out = ByteStreams.newDataOutput()
    out.writeUTF("Connect")
    out.writeUTF(server)

    sendPluginMessage(Transfer.plugin, "BungeeCord", out.toByteArray())
}

object Transfer {
    lateinit var plugin: JavaPlugin

    fun init(plugin: JavaPlugin) {
        this.plugin = plugin
        plugin.logger.info("✅ Transfer-API initialized")
        plugin.server.messenger.registerOutgoingPluginChannel(plugin, "BungeeCord")
    }

    fun shutdown() {
        plugin.logger.info("🔴 Transfer-API shutdown")
        plugin.server.messenger.unregisterOutgoingPluginChannel(plugin, "BungeeCord")
    }
}