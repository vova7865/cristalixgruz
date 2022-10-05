package com.vova7865.cristalixgruz

import CargoStatistics
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CristalixCargoPlugin : JavaPlugin() {
    lateinit var config: CargoConfig
        private set
    lateinit var stats: CargoStatistics
        private set
    lateinit var cargoManager: CargoManager
        private set

    override fun onEnable() {
        if (!dataFolder.exists())
            dataFolder.mkdirs()

        this.saveDefaultConfig()
        config = CargoConfig(getConfig())

        stats = CargoStatistics(File(dataFolder, "stats.json"))

        cargoManager = CargoManager(this)
    }

    override fun onDisable() {

    }
}