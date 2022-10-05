package com.vova7865.cristalixgruz

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.configuration.file.FileConfiguration

class CargoConfig(
    config: FileConfiguration
) {
    val world: World = Bukkit.getWorld(config.getString("world"))
    val cargoLocations: List<Location> = config.getStringList("cargo-locations").map { stringToLocation(it, world) }
    val dropoffLocations: List<Location> = config.getStringList("dropoff-locations").map { stringToLocation(it, world) }
    val cargoMaterial: Material = Material.getMaterial(config.getString("cargo-material"))
}

private fun stringToLocation(str: String, world: World): Location {
    val (x, y, z) = str.split(" ").take(3).map { it.toDouble() }
    return Location(world, x, y, z)
}
