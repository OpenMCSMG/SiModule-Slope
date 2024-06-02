package cn.nostmc.slope.listener

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.nostmc.slope.Slope
import cn.nostmc.slope.Slope.maxHight
import cn.nostmc.utils.BossBar
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

object PlayerBukkithandle : Listener {

    @EventHandler
    fun onPlayerMove(e: BlockBreakEvent) {
        e.isCancelled = true
    }


    @EventHandler
    fun onPlayerInteract(e: BlockPlaceEvent) {
        e.isCancelled = true
    }


    @EventHandler
    fun onDie(e: org.bukkit.event.entity.PlayerDeathEvent) {
        Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
            e.entity.spigot().respawn()
            e.entity.respawn()
        }, 5L)
    }


    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        // 被揍的人直接弹飞 以视角的反方向
        if (event.entity !is Player) return
        val entity = event.entity
        entity.velocity = entity.location.subtract(event.damager.location).toVector().normalize().multiply(
            cyanPlugin.config.getDouble("Velocity", 2.0)
        )
    }

    @EventHandler
    fun jumpBuffingNoDamage(e: org.bukkit.event.entity.EntityDamageEvent) {
        // 有跳跃效果无摔落伤害
        if (e.cause == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
            val ent = e.entity
            if (ent is Player) {
                val pe = ent.getPotionEffect(org.bukkit.potion.PotionEffectType.JUMP)
                if (pe != null) {
                    e.isCancelled = true
                }
            }
        }
    }


    fun Player.respawn() {
        this.health = this.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)!!.baseValue
        this.velocity = org.bukkit.util.Vector(0.0, 0.0, 0.0)
        val locList = mutableListOf<org.bukkit.Location>()
        cyanPlugin.config.getStringList("SpawnLocation").forEach {
            val split = it.split(",")
            locList.add(
                org.bukkit.Location(
                    Slope.mapWorld,
                    split[0].toDouble(),
                    split[1].toDouble(),
                    split[2].toDouble(),
                    split[3].toFloat(),
                    split[4].toFloat()
                )
            )
        }
        val loc = locList.random()
        this.teleport(loc)
        gameMode = org.bukkit.GameMode.ADVENTURE
    }

    fun Player.nextTemplate() {
        val t = cyanPlugin.config.getString("GameEnd")!!.split(",")
        val loc = org.bukkit.Location(Slope.mapWorld, t[0].toDouble(), t[1].toDouble(), t[2].toDouble())
        this.teleport(loc)
    }


    val bar = mutableMapOf<Player, BossBar>()


    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        e.player.respawn()
        bar[e.player] = BossBar(e.player, "§6进展高度", 0.0f, BossBar.Color.PINK, BossBar.Style.PROGRESS)
        Bukkit.createBossBar("§6进展高度", BarColor.BLUE, BarStyle.SOLID).apply {
            addPlayer(e.player)
            val bar = this
            object : BukkitRunnable() {
                override fun run() {
                    bar.setTitle("§6完成了${Slope.templete}个回合")
                }
            }.runTaskTimer(cyanPlugin, 0L, 20L)
        }
    }


    //  想你了骚操作 https://github.com/SakuraKoi/BridgingAnalyzer
    @EventHandler
    fun onMove(e: org.bukkit.event.player.PlayerMoveEvent) {
        val player = e.player
        val y = e.to!!.y
        val bar = bar[player]!!
        // 算出小于1的完成进度值
        val rawPercent = y.toFloat() / maxHight
        bar.percent = rawPercent.coerceIn(0.0f, 1.0f)
        bar.text = "§6进展高度: $y / $maxHight "
        // 如果y轴小于-32直接传送到出生点
        if (y < -48) {
            player.respawn()
        }
        //脚下的方块是绿宝石方块就加血
        if (player.location.subtract(0.0, 1.0, 0.0).block.type == org.bukkit.Material.EMERALD_BLOCK) {
            player.health = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)!!.baseValue
            player.sendTitle("§a加血血量加满", "", 10, 20, 10)
        }
    }


}