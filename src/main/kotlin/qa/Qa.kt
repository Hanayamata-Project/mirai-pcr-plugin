package com.hcyacg.hanayamata.qa

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.hcyacg.hanayamata.config.Config.logger
import com.hcyacg.hanayamata.dto.Qa
import com.hcyacg.hanayamata.utils.DataUtil
import com.hcyacg.hanayamata.utils.ImageUtil
import com.hcyacg.hanayamata.utils.Method
import com.hcyacg.hanayamata.utils.RequestUtil
import io.ktor.http.*
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.QuoteReply
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.Headers
import java.util.regex.Matcher
import java.util.regex.Pattern

object Qa {
    private val headers = Headers.Builder()

    suspend fun load(event: GroupMessageEvent) {
        val message = event.message.contentToString()

        val result = RequestUtil.requestObject(
            Method.GET,
            "https://api.pcr.fxmoe.com/qas/list",
            null,
            headers.add("referer", "https://pcr.fxmoe.com/").build(),
            logger
        ) ?: return
        val array = JSONArray.parseArray(result.getString("data"))
        for (data in array) {
            val qa = JSON.parseObject(data.toString(), Qa::class.java)

            val send: Pattern = Pattern.compile(qa.expression)
            val sendRegex: Matcher = send.matcher(message)
            if (sendRegex.find()) {
                val regexHtml = "(?!<(img).*?>)<.*?>"
                val p_html: Pattern = Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE)
                val m_html: Matcher = p_html.matcher(qa.html)
                val quoteReply = QuoteReply(event.message)
                var message: MessageChain = quoteReply.plus("")
                val split = m_html.replaceAll("").split("\n")
                for ((index, it) in split.withIndex()) {
                    if (it.contains("<img src=")) {
                        val uri = DataUtil.getSubString(it, "<img src=\"", "\" alt")
                        val toExternalResource = ImageUtil.getImage(uri!!).toByteArray().toExternalResource()
                        val imageId: String = toExternalResource.uploadAsImage(event.group).imageId
                        toExternalResource.close()

                        message = if (index + 1 >= split.size) {
                            message.plus(Image(imageId))
                        } else {
                            message.plus(Image(imageId)).plus("\n")
                        }
                    } else {
                        message = if (index + 1 >= split.size) {
                            message.plus(it)
                        } else {
                            message.plus(it).plus("\n")
                        }
                    }
                }
                event.subject.sendMessage(message)
            }
        }
    }
}