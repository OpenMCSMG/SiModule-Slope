package cn.nostmc.slope.entity

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import dev.lone.itemsadder.api.CustomMob
import dev.lone.itemsadder.api.ItemsAdder
import org.bukkit.Bukkit
import org.bukkit.HeightMap
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.metadata.MetadataValue
import org.bukkit.scheduler.BukkitRunnable


object MyCustomerEntity {

    /**
     * 只管负责告诉服务器生成实体与让他移动到指定位置
     * @param targetLocation 目标位置
     * @param spawnLocation 生成位置
     * @param speed 移动速度
     */
    // 在spawn方法中启动定时任务
    fun spawn(spawnLocation: Location, targetLocation: Location, speed: Double = 1.5, type: EntityType) {
        // spawn一个MineCart
        val le = spawnLocation.world!!.spawnEntity(spawnLocation, EntityType.MINECART) as Minecart
        le.maxSpeed = speed
        le.isInvulnerable = false
        le.isSlowWhenEmpty = false
        le.setGravity(true)
        le.customName = "§6小推车"
        val mob = le.location.world!!.spawnEntity(le.location, type)
        le.addPassenger(mob)
        MinecartMover(le, targetLocation, speed).runTaskTimer(cyanPlugin, 0L, 0L)
    }

    fun iaSpawn(spawnLocation: Location, targetLocation: Location, speed: Double = 1.5, mobName: String) {
        // spawn一个MineCart
        val le = spawnLocation.world!!.spawnEntity(spawnLocation, EntityType.MINECART) as Minecart
        le.maxSpeed = speed
        le.isInvulnerable = false
        le.isSlowWhenEmpty = false
        le.setGravity(true)
        le.customName = "§6小推车"
        val mob = CustomMob.spawn(mobName, spawnLocation)
        if (mob != null) {
            le.addPassenger(mob.entity)
        }
        MinecartMover(le, targetLocation, speed).runTaskTimer(cyanPlugin, 0L, 0L)
    }


    fun Player.falldownTNT(amount: Int,  addHeight: Double, boomTicket: Int) {
        // 在玩家头上
        for (i in 0 until amount) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                val newLoc = this.location.clone().add(0.0, addHeight, 0.0)
                val le = newLoc.world!!.spawnEntity(newLoc, EntityType.PRIMED_TNT) as org.bukkit.entity.TNTPrimed
                le.fuseTicks = boomTicket
                object : BukkitRunnable() {
                    override fun run() {
                        if (le.isDead) {
                            le.world.spawnParticle(org.bukkit.Particle.FIREWORKS_SPARK, le.location, 100)
                            le.remove()
                            cancel()
                        } else {
                            le.world.playEffect(le.location, org.bukkit.Effect.MOBSPAWNER_FLAMES, -1)
                        }
                    }
                }.runTaskTimer(cyanPlugin, 0L, 10L)
            }, (20..40).random().toLong())
        }

    }


    /**
     * 以玩家的位置生成 生物小推车
     */
    fun Player.useOffset(offset: Double, ty: EntityType) {
        // 根据配置的固定朝向生成实体
        val spawnLocation = this.location.offset(offset)
        val targetLocation = this.location.offset(opposite(), offset)
        Bukkit.getConsoleSender().sendMessage("§a生成位置: $spawnLocation, 目标位置: $targetLocation")
        val le = spawn(spawnLocation, targetLocation, type = ty)
        Bukkit.getConsoleSender().sendMessage("§a生成实体: $le")
    }


}