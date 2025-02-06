package xyz.yamida.paper.api.transfer

import org.bukkit.plugin.java.JavaPlugin

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