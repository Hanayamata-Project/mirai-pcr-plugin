package com.hcyacg.hanayamata

import com.hcyacg.hanayamata.bigfun.BigFunCenter
import com.hcyacg.hanayamata.bilibili.BiliBiliCenter
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info


object Pcr : KotlinPlugin(
    JvmPluginDescription(
        id = "com.hcyacg.hanayamata.pcr",
        name = "花舞组PCR插件",
        version = "1.0",
    ) {
        author("Nekoer")
        info("""花舞组PCR插件""")
    }
) {

    override fun PluginComponentStorage.onLoad() {
        BigFunCenter.load()
        BiliBiliCenter.load()
    }

    override fun onEnable() {
        logger.info { "$name 已加载" }
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> { event ->
            {
                //event.message.content
//            event.subject.sendMessage()

            }
        }

    }
}