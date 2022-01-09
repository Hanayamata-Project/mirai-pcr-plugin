package com.hcyacg.hanayamata.feedback

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.hcyacg.hanayamata.config.Config.logger
import com.hcyacg.hanayamata.entity.Feedback
import com.hcyacg.hanayamata.utils.Method
import com.hcyacg.hanayamata.utils.RequestUtil
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.lang.RuntimeException
import java.util.*


object FeedbackCenter {
    private val headers = Headers.Builder()
    private var requestBody: RequestBody? = null


    fun send(event: GroupMessageEvent) {
        val message = event.message.contentToString().replaceFirst("反馈 ", "")
        val feedback = Feedback(
            null,
            message,
            event.sender.id,
            event.group.id,
            event.bot.id,
            Calendar.Builder().build().timeInMillis.toString(),
            0
        )

        requestBody =
            RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), JSON.toJSON(feedback).toString())

        val result = RequestUtil.requestObject(
            Method.PUT,
            "https://api.pcr.fxmoe.com/feedbacks/feedback",
            requestBody,
            headers.add("referer", "https://pcr.fxmoe.com/").build(),
            logger
        )

        if (null != result) {
            val feedbackVo = JSON.parseObject(result["data"].toString(), Feedback::class.java)
            runBlocking {
                event.subject.sendMessage("提交成功,反馈号为${feedbackVo.id}")
            }
        }
    }

    fun get(event: GroupMessageEvent) {
        try {
            val message = event.message.contentToString().replaceFirst("获取反馈 ", "")

            val regex = Regex("[0-9]")

            if (!regex.containsMatchIn(message)) {
                runBlocking {
                    event.subject.sendMessage("请输入数字, 例: 获取反馈 1")
                }
                return
            }


            val result = RequestUtil.requestObject(
                Method.GET,
                "https://api.pcr.fxmoe.com/feedbacks/feedback/info/$message",
                requestBody,
                headers.add("referer", "https://pcr.fxmoe.com/").build(),
                logger
            )

            if (null != result) {
                val feedbackVo = JSON.parseObject(result["data"].toString(), Feedback::class.java)
                runBlocking {
                    if (event.sender.id != feedbackVo.qqId) {
                        event.subject.sendMessage("非常抱歉,该反馈的反馈者不是您~")
                        return@runBlocking
                    } else {
                        event.subject.sendMessage(
                            PlainText("内容: ${feedbackVo.content}").plus("\n")
                                .plus(
                                    "状态: ${
                                        if (feedbackVo.status == 0) "未解决" else "已解决"
                                    }"
                                )
                        )
                    }
                }
            } else {
                throw RuntimeException("未获取到数据")
            }
        } catch (e: Exception) {
            logger.error(e.message)
            runBlocking {
                event.subject.sendMessage("未获取到数据")
            }
        }
    }

}