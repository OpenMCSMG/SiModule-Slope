package cn.nostmc.slope.superitem

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object FishHook : Listener {

    val pl = mutableListOf<Player>()
    val useAmount = mutableMapOf<Player, Int>()


    fun hook(): ItemStack {
        val hook = ItemStack(Material.FISHING_ROD)
        val meta = hook.itemMeta
        meta?.setDisplayName("§6抓钩")
        // UNbreak
        meta?.isUnbreakable = true
        hook.itemMeta = meta
        return hook
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item
        if (item != null && item == hook()) {
            if (!useAmount.containsKey(player) || useAmount[player]!! <= 0) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c你没有抓钩次数"))
                return
            } else {
                if (!pl.contains(player)) {
                    event.isCancelled = true
                    player.launchProjectile(FishHook::class.java)
                    pl.add(player)
                }
            }
        }
    }




    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) {
        val player = event.player
        if (pl.contains(player)) {
            val directionVector = event.hook.location.subtract(player.location).toVector()
            player.velocity = directionVector
            pl.remove(player)
            useAmount[player] = useAmount[player]!! - 1
        }
    }

}