package cn.nostmc.slope

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.nostmc.slope.listener.PlayerBukkithandle
import cn.nostmc.slope.listener.PlayerBukkithandle.respawn
import cn.nostmc.slope.listener.WorldProtect
import cn.nostmc.slope.service.ConfigEntry
import cn.nostmc.slope.service.EntityPushTask
import cn.nostmc.slope.superitem.FishHook
import cn.nostmc.utils.BossBar
import cn.nostmc.utils.Scoreboard
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable

object Slope {

    lateinit var mapWorld : World
    var maxHight = 0
    private lateinit var  block : Material
    fun start() {
        ConfigEntry.create()
        maxHight = cyanPlugin.config.getInt("MaxHight", 109)
        block = Material.matchMaterial(cyanPlugin.config.getString("EndBlock", Material.REDSTONE_BLOCK.name)!!) ?: Material.REDSTONE_BLOCK
        // 如果默认加载的
        val 默认地图名字 = cyanPlugin.config.getString("默认游戏地图名字", "Slope")!!
        Bukkit.getWorlds().forEach() {
            if (it.name == 默认地图名字) {
                mapWorld = it
            }
        }
        if (!this::mapWorld.isInitialized) {
            val w = Bukkit.createWorld(WorldCreator.name(默认地图名字))
            if (w != null) {
                mapWorld = w
            } else {
                throw Exception("无法创建默认地图")
            }
        }
        // 实体碰在一起就会被弹飞
        Bukkit.getScheduler().scheduleSyncRepeatingTask(cyanPlugin, EntityPushTask(), 0L, 0L)
        mapWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        mapWorld.setGameRule(GameRule.DO_MOB_LOOT, false)
        // 禁止火势蔓延s与烧毁
        mapWorld.setGameRule(GameRule.FIRE_DAMAGE, false)
        mapWorld.setGameRule(GameRule.DO_FIRE_TICK, false)
        // 禁止天气变化
        mapWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        mapWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        // 注册事件监听器
        cyanPlugin.server.pluginManager.registerEvents(PlayerBukkithandle, cyanPlugin)
        cyanPlugin.server.pluginManager.registerEvents(WorldProtect, cyanPlugin)
        cyanPlugin.server.pluginManager.registerEvents(FishHook, cyanPlugin)
        BossBar.init(cyanPlugin)
        Scoreboard.init(cyanPlugin)
        status()
        cyanPlugin.config.getStringList("SpawnLocation").forEach {
            val split = it.split(",")
            mapWorld.spawnLocation = Location(
                mapWorld,
                split[0].toDouble(),
                split[1].toDouble(),
                split[2].toDouble(),
                split[3].toFloat(),
                split[4].toFloat()
            )
        }
    }

    val entityList = listOf(
        EntityType.ZOMBIE,
        EntityType.SKELETON,
        EntityType.CREEPER,
        EntityType.SPIDER,
        EntityType.CAVE_SPIDER,
        EntityType.ENDERMAN,
        EntityType.WITCH,
        EntityType.BLAZE,
//        EntityType.GHAST,
        EntityType.PIGLIN,
        EntityType.WITHER_SKELETON,
//        EntityType.SHULKER,
        EntityType.ILLUSIONER,
//        EntityType.VEX,
        EntityType.EVOKER,
//        EntityType.STRAY,
        EntityType.HUSK,
        EntityType.ZOMBIE_VILLAGER,
        EntityType.VINDICATOR,
//        EntityType.SQUID
    )




    var templete = 0
    val KEEP = 10
    var keepTime = KEEP
    var checking = false

    private fun status() {
        Bukkit.getConsoleSender().sendMessage("§a[斜坡] 插件已经加载统计")
        object : BukkitRunnable() {
            override fun run() {
                if(Bukkit.getOnlinePlayers().isEmpty()) return
                val p = Bukkit.getOnlinePlayers().first()
                // 获取 玩家脚踩的方块
                val material = p.location.add(0.0, -1.0, 0.0).block.type
                if (material == block) {
                    if (checking) {
                        keepTime--
                        p.sendTitle("加油", "§a还剩${keepTime}秒", 10, 20, 10)
                        if (keepTime <= 0) {
                            checking = false
                            keepTime = KEEP
                            templete++
                            p.sendTitle("完成一个回合", "§a已经成功完成${templete}回合", 10, 20, 10)
                            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                                p.respawn()
                            }, 0)
                        }
                    } else {
                        checking = true
                        p.sendTitle("", "§a加油离成功就差${KEEP}秒了", 10, 20, 10)
                    }
                } else {
                    if (checking) {
                        checking = false
                        keepTime = KEEP
                        p.sendTitle("§c哎呀", "§f没有坚持住~", 10, 20, 10)
                    }
                }
            }
        }.runTaskTimerAsynchronously(cyanPlugin, 0L, 20L)
    }

}