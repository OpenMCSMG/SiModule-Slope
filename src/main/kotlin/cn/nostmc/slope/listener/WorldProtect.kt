package cn.nostmc.slope.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

object WorldProtect : Listener {


    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        // 清空爆炸的方块破坏列表，防止爆炸破坏方块
        event.blockList().clear()
        event.location.world!!.spawnParticle(org.bukkit.Particle.FIREWORKS_SPARK, event.location, 300)
    }




}