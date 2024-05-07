package cn.nostmc.slope

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.nostmc.slope.command.Mode
import cn.nostmc.slope.entity.MyCustomerEntity.falldownTNT
import cn.nostmc.slope.entity.MyCustomerEntity.useOffset
import cn.nostmc.slope.listener.PlayerBukkithandle.nextTemplete
import cn.nostmc.slope.listener.PlayerBukkithandle.respawn
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class GameHandle {

    @Mode("simple")
    fun simple(p: Player, amount: Double, radius: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        // 慢速生成
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                p.useOffset(radius, EntityType.ZOMBIE)
            }, 20)
        }
    }

    @Mode("2")
    fun giveArrowAndBow(p: Player, amount: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.BOW))
        p.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.ARROW, amount.toInt()))
    }

    @Mode("3")
    fun giveArrow(p: Player, amount: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.inventory.addItem(org.bukkit.inventory.ItemStack(org.bukkit.Material.ARROW, amount.toInt()))
    }

    @Mode("4")
    fun batchPushCart(p: Player, amount: Double, addLength: Double, mob: String, title: String, subTitle: String) {
        // 生成小推车
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                cn.nostmc.slope.entity.BatchSpawn.start(p, addLength.toInt(), EntityType.valueOf(mob))
            }, 20)
        }
    }

    @Mode("5")
    fun batchPushCart(p: Player, amount: Double, addLength: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        val random = Slope.entityList.random()
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                cn.nostmc.slope.entity.BatchSpawn.start(p, addLength.toInt(), random)
            }, 20)
        }
    }

    @Mode("6")
    fun blind(p: Player, second: Double, amplifier: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, (second * 20).toInt(), amplifier.toInt()))
    }

    @Mode("7")// 重生
    fun respawn(p: Player, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.respawn()
    }

    @Mode("8") // 传送到结束
    fun teleportEnd(p: Player, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.nextTemplete()
    }

    @Mode("9") // 加值
    fun addTemplete(p: Player, amount: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        Slope.templete += amount.toInt()
    }

    @Mode("10") // 减值
    fun spawnTNT(p: Player, amount: Double, addHeight: Double,boomTicket: Int, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.falldownTNT(amount.toInt(), addHeight, boomTicket)
    }

    @Mode("11") // 根据时间每秒 雷击倒玩家
    fun strikeLightning(p: Player, second: Double, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        for (ii in 0 until second.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                p.world.strikeLightning(p.location)
            }, 20)
        }
    }

    @Mode("12") // 自定义个metadate
    fun advanceBatchPushCart(p: Player, amount: Double, addLength: Double, title: String, subTitle: String, iaData: String) {
        // 生成小推车
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        for (ii in 0 until amount.toInt()) {
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                cn.nostmc.slope.entity.BatchSpawn.advanceStart(p, addLength.toInt(), iaData)
            }, 20)
        }
    }

    @Mode("13") // 回血
    fun heal(p: Player, title: String, subTitle: String) {
        p.sendTitle(title
            .replace("&","§"), subTitle
            .replace("&","§"), 10, 20, 10)
        p.health = p.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)!!.baseValue
    }

    @Mode("14") // 增加条约药水
    fun jumoANdSpeed(p: Player, second: Double, amplifier: Int, title: String, subTitle: String) {
        p.sendTitle(title.replace("&", "§"), subTitle.replace("&", "§"), 10, 20, 10)

        val jumpEffect = p.getPotionEffect(org.bukkit.potion.PotionEffectType.JUMP)
        val newJumpEffectDuration = if (jumpEffect != null) (jumpEffect.duration + (second * 20)).toInt() else (second * 20).toInt()
        p.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, newJumpEffectDuration, amplifier), true)

        val speedEffect = p.getPotionEffect(org.bukkit.potion.PotionEffectType.SPEED)
        val newSpeedEffectDuration = if (speedEffect != null) (speedEffect.duration + (second * 20)).toInt() else (second * 20).toInt()
        p.addPotionEffect(org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, newSpeedEffectDuration, amplifier), true)
    }


}