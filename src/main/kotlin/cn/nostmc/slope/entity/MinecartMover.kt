package cn.nostmc.slope.entity

import org.bukkit.Location
import org.bukkit.entity.Minecart
import org.bukkit.scheduler.BukkitRunnable

class MinecartMover(
    private val minecart: Minecart,
    private val targetLocation: Location,
    private val speed: Double
) : BukkitRunnable() {
    override fun run() {
        if (minecart.location.distance(targetLocation) <= 1.0
                 || minecart.location.y <= targetLocation.y
            || minecart.isDead
            || minecart.passengers.isEmpty()
            ) {
            minecart.passengers.forEach {
                it.remove()
            }
            this.cancel()
            minecart.remove()
        } else {
            // 因为设置了斜坡铁轨正常推动到targetLocation
            val direction = targetLocation.toVector().subtract(minecart.location.toVector()).normalize()
            minecart.velocity = direction.multiply(speed)
        }
    }
}

