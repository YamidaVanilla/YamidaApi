package xyz.yamida.paper.api.ext

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import xyz.yamida.paper.api.transfer.Transfer

fun Player.transferToServer(server: String) {
    val out = ByteStreams.newDataOutput()
    out.writeUTF("Connect")
    out.writeUTF(server)

    sendPluginMessage(Transfer.plugin, "BungeeCord", out.toByteArray())
}
