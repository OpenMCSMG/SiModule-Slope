package cn.nostmc.slope.entity

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue

/**
 * 以玩家为中心的那个 比如说固定视角为-Z
 * 那么 整个Z的 会按照 Z-Radius 一个数一个列
 * X与Y是以斜坡为基准做变动的
 */
object BatchSpawn {

    fun start(p: Player, addLength: Int, mob : EntityType) {
        val getAllLocation = getBaseLocation(p, addLength)
        // 打乱getAllLocation的顺序
        val list = getAllLocation.toList().shuffled().toMap()
        list.forEach { (t, u) ->
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                MyCustomerEntity.spawn(t, u, type = mob)
            }, (3..30).random().toLong())
        }
    }

    fun advanceStart(p: Player, addLength: Int, iaModel: String) {
        val getAllLocation = getBaseLocation(p, addLength)
        val map = mutableMapOf<String, MetadataValue>()
        map["ItemsAdderMob"] = FixedMetadataValue(cyanPlugin, iaModel)
        // 打乱getAllLocation的顺序
        val list = getAllLocation.toList().shuffled().toMap()
        list.forEach { (t, u) ->
            Bukkit.getScheduler().runTaskLater(cyanPlugin, Runnable {
                MyCustomerEntity.iaSpawn(t, u, mobName = iaModel)
            }, (3..30).random().toLong())
        }
    }

    // 获取基准List Location 与结尾Location就是起始位置
    fun getBaseLocation(p: Player, addLength: Int): MutableMap<Location, Location> {
        val emptyMap = mutableMapOf<Location, Location>()

        val listRadius = cyanPlugin.config.getString("List-Radius", "-142;152")
        val list = listRadius!!.split(";")
        val min = minOf(list[0].toInt(), list[1].toInt())
        val max = maxOf(list[0].toInt(), list[1].toInt())

        println("min: $min, max: $max")

        val startDefaultLocation = p.location.offset(addLength.toDouble())
        val endDefaultLocation =p.location.offset(opposite(), addLength.toDouble())

        when (cyanPlugin.config.getString("Direction", "-X")!!) {
            "-Z", "+Z"  -> {
                for (i in min..max) {
                    val startX = i.toDouble()
                    val startZ = startDefaultLocation.z
                    val startLocation = Location(p.world, startX, startDefaultLocation.y, startZ)
                    val endX = i.toDouble()
                    val endZ = endDefaultLocation.z
                    val endLocation = Location(p.world, endX, endDefaultLocation.y, endZ)
                    emptyMap[startLocation] = endLocation
                }
            }

            "-X" , "+X"-> {
                for (i in min..max) {
                    val startX = startDefaultLocation.x
                    val startZ = i.toDouble()
                    val startLocation = Location(p.world, startX, startDefaultLocation.y, startZ)
                    val endX = endDefaultLocation.x
                    val endZ = i.toDouble()
                    val endLocation = Location(p.world, endX, endDefaultLocation.y, endZ)
                    emptyMap[startLocation] = endLocation
                }
            }
        }





        return emptyMap
    }


}