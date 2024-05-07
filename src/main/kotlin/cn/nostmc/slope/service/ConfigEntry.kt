package cn.nostmc.slope.service

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import org.bukkit.Material

object ConfigEntry {

    fun create() {
        cyanPlugin.config.addDefault("默认游戏地图名字", "Slope")
        cyanPlugin.config.addDefault("SpawnLocation", listOf("0,0,0,0,0", "0,0,0,0,0"))
        cyanPlugin.config.addDefault("MaxHight", 109)
        cyanPlugin.config.addDefault("EndBlock", Material.REDSTONE_BLOCK.name)
        cyanPlugin.config.addDefault("Direction", "-X")
        cyanPlugin.config.addDefault("Velocity", "2")
        cyanPlugin.config.addDefault("List-Radius", "-142;-152")
        cyanPlugin.config.addDefault("GameEnd","-5,207,-147")

        val header = mutableListOf<String>()
        header.add("Direction: North (+Z), West (-X), South (-Z), East (+X)")
        header.add("MaxHight 是方便统计bar 的还是要以结束方块为准")
        header.add("Velocity 是击飞的值")
        cyanPlugin.config.options().copyDefaults(true)
        cyanPlugin.config.options().setHeader(header)
        cyanPlugin.config.save(cyanPlugin.yaml)
    }


}
