package cn.nostmc.slope.service

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.nostmc.slope.Slope
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType

class EntityPushTask : Runnable {

    override fun run() {
        // 对每个实体进行检查
        for (entity in Bukkit.getOnlinePlayers()) {
            // 获取实体附近的其他实体
            val nearbyEntities = entity.getNearbyEntities(0.5, 0.5, 0.5)
            // 对每个附近的实体进行检查
            for (nearbyEntity in nearbyEntities) {
                if (nearbyEntity.type != EntityType.MINECART || Slope.entityList.contains(nearbyEntity.type))  {
                    entity.velocity = entity.location.subtract(nearbyEntity.location).toVector().normalize().multiply(
                        cyanPlugin.config.getDouble("Velocity", 2.0)
                    )
                }
            }
        }
    }
}