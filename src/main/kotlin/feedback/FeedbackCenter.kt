package com.hcyacg.hanayamata.feedback

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.hcyacg.hanayamata.config.Config.logger
import com.hcyacg.hanayamata.entity.Feedback
import com.hcyacg.hanayamata.utils.Method
import com.hcyacg.hanayamata.utils.RequestUtil
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.event.events.GroupMessageEvent
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.*


object FeedbackCenter {
    private val headers = Headers.Builder()
    private var requestBody: RequestBody? = null

    fun load(event: GroupMessageEvent) {
        val message = event.message.contentToString().replaceFirst("反馈 ", "")
        println(message)
        val feedback = Feedback(
            null,
            message,
            event.sender.id,
            event.group.id,
            event.bot.id,
            Calendar.Builder().build().timeInMillis.toString()
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


}