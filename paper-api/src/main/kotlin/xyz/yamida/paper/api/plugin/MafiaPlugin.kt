package xyz.yamida.paper.api.plugin

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.megadev.mafia.server.components.Component
import xyz.megadev.mafia.server.components.MessagingListener
import xyz.megadev.mafia.server.components.ServicesManager
import xyz.yamida.paper.api.services.Service
import xyz.yamida.paper.api.transfer.Transfer

abstract class MafiaPlugin : JavaPlugin() {
    abstract val components: Collection<Component>

    override fun onEnable() {
        Transfer.init(this)
        components.forEach { component ->
            if (component is Listener) {
                server.pluginManager.registerEvents(component, this)
            }
            if (component is Service) {
                ServicesManager.registerService(component)
                component.onEnable()
            }
            if (component is MessagingListener) {
                server.messenger.registerIncomingPluginChannel(this, component.channel, component)
            }
        }
        whenEnable()
    }

    override fun onDisable() {
        components.forEach { component ->
            if (component is Service) {
                ServicesManager.unregisterService(component)
                component.onDisable()
            }
            if (component is Listener) {
                HandlerList.unregisterAll(component)
            }
            if (component is MessagingListener) {
                server.messenger.unregisterIncomingPluginChannel(this, component.channel)
            }
        }
        Transfer.shutdown()
        whenDisable()
    }

    open fun whenEnable() {}
    open fun whenDisable() {}
}