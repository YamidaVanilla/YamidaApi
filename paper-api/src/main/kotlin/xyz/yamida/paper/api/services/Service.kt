package xyz.yamida.paper.api.services

import xyz.megadev.mafia.server.components.Component

interface Service : Component {
    fun onEnable() {}
    fun onDisable() {}
}