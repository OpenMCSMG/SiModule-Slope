package cn.nostmc.slope.command

import cn.nostmc.slope.GameHandle
import cn.nostmc.slope.entity.MyCustomerEntity.falldownTNT
import cn.nostmc.slope.entity.MyCustomerEntity.useOffset
import dev.lone.itemsadder.api.CustomEntity
import dev.lone.itemsadder.api.CustomMob
import org.bukkit.command.Command

object SlopeTest : Command("slope") {
    override fun execute(sender: org.bukkit.command.CommandSender, label: String, args: Array<out String>): Boolean {
        //  获取玩家
        val p = sender as org.bukkit.entity.Player
        //  以玩家视角的反向击退半格
        if (args.isEmpty()) {
            p.sendMessage("§c请输入模式")
        } else {
            when (args[0]) {
                "test" -> {
                    val a = CustomMob.spawn("the_man_from_the_fog", p.location)
                    println(a?.namespace)
                }


                else ->{
                    val hand = GameHandle()
                    val methods = hand.javaClass.declaredMethods
                    for (method in methods) {
                        val mode = method.getAnnotation(Mode::class.java)
                        if (mode != null) {
                            if (mode.value == args[0]) {
                                val info = args.sliceArray(1 until args.size)
                                val parameterTypes = method.parameterTypes
                                val convertedArgs = Array<Any>(info.size) { i ->
                                    when (parameterTypes[i + 1]) { // i + 1 because the first parameter is Player
                                        Double::class.java -> info[i].toDouble()
                                        Int::class.java -> info[i].toInt()
                                        String::class.java -> info[i]
                                        else -> throw IllegalArgumentException("Unsupported parameter type")
                                    }
                                }
                                method.invoke(hand, p, *convertedArgs)
                                return true
                            }
                        }
                    }
                    p.sendMessage("§c未找到模式")
                }

            }



        }
        return true
    }




}