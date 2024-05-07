@file:Suppress("unused")

package cn.nostmc.slope.entity

import cn.cyanbukkit.climb.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import org.bukkit.Location



//   根据
//    val rotation = (player.location.yaw + 180) % 360
//    rotation < 45 || rotation > 315 -> "North (+Z)"
//    rotation < 135 -> "West (-X)"
//    rotation < 225 -> "South (-Z)"
//    else -> "East (+X)"



fun Location.offset(number: Double): Location = when (cyanPlugin.config.getString("Direction", "-X")) {
    "-Z" -> {
        val x = this.blockX
        val z = this.blockZ - number
        this.world!!.getHighestBlockAt(x, z.toInt()).location.add(0.5, 1.0, 0.5)
    }

    "+Z" -> {
        val x = this.blockX
        val z = this.blockZ + number
        this.world!!.getHighestBlockAt(x, z.toInt()).location.add(0.5, 1.0, 0.5)
    }

    "-X" -> {
        val x = this.blockX - number
        val z = this.blockZ
        this.world!!.getHighestBlockAt(x.toInt(), z).location.add(0.5, 1.0, 0.5)
    }

    "+X" -> {
        val x = this.blockX + number
        val z = this.blockZ
        this.world!!.getHighestBlockAt(x.toInt(), z).location.add(0.5, 1.0, 0.5)
    }

    else -> this
}


fun Location.offset(text: String, number: Double): Location = when (text) {
    "-Z" -> {
        val x = this.blockX
        val z = this.blockZ - number
        this.world!!.getHighestBlockAt(x, z.toInt()).location.add(0.5, 1.0, 0.5)
    }

    "+Z" -> {
        val x = this.blockX
        val z = this.blockZ + number
        this.world!!.getHighestBlockAt(x, z.toInt()).location.add(0.5, 1.0, 0.5)
    }

    "-X" -> {
        val x = this.blockX - number
        val z = this.blockZ
        this.world!!.getHighestBlockAt(x.toInt(), z).location.add(0.5, 1.0, 0.5)
    }

    "+X" -> {
        val x = this.blockX + number
        val z = this.blockZ
        this.world!!.getHighestBlockAt(x.toInt(), z).location.add(0.5, 1.0, 0.5)
    }

    else -> this
}

fun opposite(): String = when (cyanPlugin.config.getString("Direction", "-X")) {
    "-Z" -> "+Z"
    "+Z" -> "-Z"
    "-X" -> "+X"
    "+X" -> "-X"
    else -> {
        cyanPlugin.logger.warning("Direction is not valid")
        "-Z"
    }
}

