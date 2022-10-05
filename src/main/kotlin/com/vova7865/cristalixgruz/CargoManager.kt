package com.vova7865.cristalixgruz

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

class CargoManager(
    private val plugin: CristalixCargoPlugin
) {
    private val availableCargo = HashMap(plugin.config.cargoLocations.associateWith { true })
    private val holdingCargo = HashMap<UUID, Location>()

    init {
        val listener = ClickBlockListener(plugin, this)
        Bukkit.getPluginManager().registerEvents(listener, plugin)

        plugin.config.cargoLocations.forEach {
            val block = plugin.config.world.getBlockAt(it)
            block.type = plugin.config.cargoMaterial
        }
    }

    fun pickup(player: Player, block: Block): Boolean {
        if (!availableCargo.contains(block.location) || !availableCargo[block.location]!!) {
            return false
        }

        if (holdingCargo.contains(player.uniqueId)) {
            player.sendMessage("У вас уже есть груз")
            return true
        }

        plugin.logger.info("pickup :: ${player.name}")
        player.sendMessage("Вы взяли груз")

        holdingCargo[player.uniqueId] = block.location
        availableCargo[block.location] = false
        plugin.config.world.getBlockAt(block.location).type = Material.BARRIER

        return true
    }

    fun dropoff(player: Player, block: Block): Boolean {
        if (!plugin.config.dropoffLocations.contains(block.location))
            return false

        if (!holdingCargo.contains(player.uniqueId)) {
            return true
        }
        plugin.stats.increment(player.uniqueId)
        val count = plugin.stats.get(player.uniqueId)
        plugin.logger.info("dropoff :: ${player.name} - $count")
        player.sendMessage("Вы доставили груз: всего $count доставлено")

        val pos = holdingCargo.remove(player.uniqueId)!!
        availableCargo[pos] = true
        plugin.config.world.getBlockAt(pos).type = plugin.config.cargoMaterial

        return true
    }
}