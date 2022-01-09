package com.hcyacg.hanayamata

import com.hcyacg.hanayamata.bigfun.BigFunCenter
import com.hcyacg.hanayamata.bilibili.BiliBiliCenter
import com.hcyacg.hanayamata.feedback.FeedbackCenter
import com.hcyacg.hanayamata.qa.Qa
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info
import org.apache.commons.lang3.StringUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


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
            run {
                if (StringUtils.isNotBlank(event.message.contentToString())) {
                    Qa.load(event);

                    val send: Pattern = Pattern.compile("(?i)^(反馈)[ ].+\$")
                    val sendRegex: Matcher = send.matcher(event.message.contentToString())
                    if (sendRegex.find()) {
                        FeedbackCenter.send(event)
                    }
                    val get: Pattern = Pattern.compile("(?i)^(获取反馈)[ ][0-9]+\$")
                    val getRegex: Matcher = get.matcher(event.message.contentToString())
                    if (getRegex.find()) {
                        FeedbackCenter.get(event)
                    }
                }
            }
        }

    }
}