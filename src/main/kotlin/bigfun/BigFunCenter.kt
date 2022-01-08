package com.hcyacg.hanayamata.bigfun

import com.alibaba.fastjson.JSON
import com.hcyacg.hanayamata.GroupSender
import com.hcyacg.hanayamata.GroupSender.sendMessage
import com.hcyacg.hanayamata.config.Config
import com.hcyacg.hanayamata.config.Config.logger
import com.hcyacg.hanayamata.entity.BigFunInfo
import com.hcyacg.hanayamata.utils.Method
import com.hcyacg.hanayamata.utils.RequestUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.RequestBody
import org.apache.commons.lang3.StringUtils
import java.util.*


object BigFunCenter {
    private val headers = Headers.Builder()
    private val requestBody: RequestBody? = null
    private var oldId: String = "0"

    fun load() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                try {
                    val result =
                        RequestUtil.requestObject(Method.GET, Config.bigFun, requestBody, headers.build(), logger)
                    val bigFunInfo = JSON.parseObject(result.toString(), BigFunInfo::class.java)

                    if (oldId.contentEquals("0")) {
                        if (bigFunInfo.data.isEmpty()) {
                            oldId = "1"
                            return
                        }

                        oldId = bigFunInfo.data[0].id
                        return
                    } else if (bigFunInfo.data.isNotEmpty() && !oldId.contentEquals(bigFunInfo.data[0].id)) {
                        runBlocking {
                            oldId = bigFunInfo.data[0].id
                            GroupSender.sendMessage(bigFunInfo)
                            delay(10000L)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    logger.error(e.message)
                }
            }
        }, Date(), 10000)
    }

}