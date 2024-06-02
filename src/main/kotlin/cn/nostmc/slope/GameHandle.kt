package cn.nostmc.slope

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.nostmc.slope.command.Mode
import cn.nostmc.slope.entity.MyCustomerEntity.falldownTNT
import cn.nostmc.slope.entity.MyCustomerEntity.useOffset
import cn.nostmc.slope.listener.PlayerBukkithandle.nextTemplate
import cn.nostmc.slope.listener.PlayerBukkithandle.respawn
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getOnlinePlayers
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable

class GameHandle {

    @Mode("1")
    fun simple(amount: Double, radius: Double) {
        // 慢速生成
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                getOnlinePlayers().forEach {
                    it.useOffset(radius, EntityType.ZOMBIE)
                }
            }, 20)
        }
    }

    @Mode("2")
    fun giveArrowAndBow(amount: Double) {
        getOnlinePlayers().forEach{
            it.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.BOW))
            it.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.ARROW, amount.toInt()))
        }
    }

    @Mode("3")
    fun giveArrow(amount: Double) {
        getOnlinePlayers().forEach{
            it.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.ARROW, amount.toInt()))
        }
    }

    @Mode("4")
    fun batchPushCart(amount: Double, addLength: Double, mob: String) {
        // 生成小推车
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                getOnlinePlayers().forEach {
                    cn.nostmc.slope.entity.BatchSpawn.start(it, addLength.toInt(), EntityType.valueOf(mob))
                }
            }, 20)
        }
    }

    @Mode("5")
    fun batchPushCart(amount: Double, addLength: Double) {
        val random = Slope.entityList.random()
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                getOnlinePlayers().forEach {
                    cn.nostmc.slope.entity.BatchSpawn.start(it, addLength.toInt(), random)
                }
            }, 20)
        }
    }

    @Mode("6")
    fun blind( second: Double, amplifier: Double) {
        Bukkit.getScheduler().runTask(cyanPlugin, Runnable {
            getOnlinePlayers().forEach {
                it.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, (second * 20).toInt(), amplifier.toInt()))
            }
        })
    }

    @Mode("7")// 重生
    fun respawn() {
        Bukkit.getScheduler().runTask(cyanPlugin, Runnable {
            getOnlinePlayers().forEach {
                it.respawn()
            }
        })
    }

    @Mode("8") // 传送到结束
    fun teleportEnd() {
        Bukkit.getScheduler().runTask(cyanPlugin, Runnable {
            getOnlinePlayers().forEach {
                it.nextTemplate()
            }
        })
    }

    @Mode("9") // 加值
    fun addTemplate(amount: Double) {
        Slope.templete += amount.toInt()
    }

    @Mode("10") // 减值
    fun spawnTNT(amount: Double, addHeight: Double,boomTicket: Int) {
        getOnlinePlayers().forEach {
            it.falldownTNT(amount.toInt(), addHeight, boomTicket)
        }
    }

    @Mode("11") // 根据时间每秒 雷击倒玩家
    fun strikeLightning(second: Double) {
        object : BukkitRunnable() {
            var temp = second
            override fun run() {
                if (temp <= 0) {
                    cancel()
                    return
                }
                temp--
                getOnlinePlayers().forEach {
                    it.world.strikeLightning(it.location)
                }
            }
        }.runTaskTimer(cyanPlugin, 0, 20)
    }

    @Mode("12") // 自定义个metadate
    fun advanceBatchPushCart(amount: Double, addLength: Double, iaData: String) {
        // 生成小推车
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                getOnlinePlayers().forEach {
                    cn.nostmc.slope.entity.BatchSpawn.advanceStart(it, addLength.toInt(), iaData)
                }
            }, 20)
        }
    }

    @Mode("13") // 回血
    fun heal() {
        Bukkit.getScheduler().runTask(cyanPlugin, Runnable {
            getOnlinePlayers().forEach {
                it.health = it.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)!!.baseValue
            }
        })
    }

    @Mode("14") // 增加条约药水
    fun jumpAndSpeed(second: Double, amplifier: Int) {
        Bukkit.getScheduler().runTask(cyanPlugin, Runnable {
            getOnlinePlayers().forEach {
                val jumpEffect = it.getPotionEffect(org.bukkit.potion.PotionEffectType.JUMP)
                val newJumpEffectDuration = if (jumpEffect != null) (jumpEffect.duration + (second * 20)).toInt() else (second * 20).toInt()
                it.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, newJumpEffectDuration, amplifier), true)
                val speedEffect = it.getPotionEffect(org.bukkit.potion.PotionEffectType.SPEED)
                val newSpeedEffectDuration = if (speedEffect != null) (speedEffect.duration + (second * 20)).toInt() else (second * 20).toInt()
                it.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, newSpeedEffectDuration, amplifier), true)
            }
        })
    }


}