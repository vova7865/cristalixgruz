package com.vova7865.cristalixgruz

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ClickBlockListener(
    private val plugin: CristalixCargoPlugin,
    private val cargoManager: CargoManager
) : Listener {
    @EventHandler
    fun onClickBlock(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player

        if (player.world != plugin.config.world) {
            return
        }

        if (event.action == Action.RIGHT_CLICK_BLOCK
            && event.hand == EquipmentSlot.HAND
            && player.inventory.itemInMainHand.type == Material.AIR
            && cargoManager.pickup(player, block)) {
            event.isCancelled = true
        }

        if (event.action == Action.PHYSICAL
            && block.type == Material.STONE_PLATE
            && cargoManager.dropoff(player, block)) {
            event.isCancelled = true
        }
    }
}