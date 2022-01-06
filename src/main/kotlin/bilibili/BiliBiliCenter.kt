package com.hcyacg.hanayamata.bilibili

import com.alibaba.fastjson.JSON
import com.hcyacg.hanayamata.GroupSender
import com.hcyacg.hanayamata.GroupSender.sendMessage
import com.hcyacg.hanayamata.config.Config
import com.hcyacg.hanayamata.entity.BiliBiliArticle
import com.hcyacg.hanayamata.entity.BiliBiliLive
import com.hcyacg.hanayamata.entity.BiliBiliVideo
import com.hcyacg.hanayamata.utils.Method
import com.hcyacg.hanayamata.utils.RequestUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.RequestBody
import java.util.*


object BiliBiliCenter {
    private val headers = Headers.Builder()
    private val requestBody: RequestBody? = null
    private lateinit var videoId: String
    private var articleId: Int = 0
    private var liveStatus: Int = -1

    fun load() {
        article()
        video()
        live()
    }

    private fun article() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val result = RequestUtil.requestObject(
                    Method.GET, Config.bilibiliArticle,
                    requestBody, headers.build(),
                    Config.logger
                )
                val biliBiliArticle = JSON.parseObject(result.toString(), BiliBiliArticle::class.java)
                if (null == biliBiliArticle.data.articles) {
                    return
                }
                if (articleId == 0) {
                    articleId = biliBiliArticle.data.articles[0].id
                    return
                } else if (articleId != biliBiliArticle.data.articles[0].id) {
                    runBlocking {
                        GroupSender.sendMessage(biliBiliArticle)
                        delay(10000L)
                    }
                }
            }
        }, Date(), 10000)
    }

    private fun video() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val result = RequestUtil.requestObject(
                    Method.GET, Config.bilibiliVideo,
                    requestBody, headers.build(),
                    Config.logger
                )
                val biliBiliVideo = JSON.parseObject(result.toString(), BiliBiliVideo::class.java)

                if (null == biliBiliVideo.data.list.vlist) {
                    return
                }

                if (!::videoId.isInitialized) {
                    videoId = biliBiliVideo.data.list.vlist[0].bvid
                    return
                } else if (videoId != biliBiliVideo.data.list.vlist[0].bvid) {
                    runBlocking {
                        GroupSender.sendMessage(biliBiliVideo)
                        delay(10000L)
                    }
                }
            }
        }, Date(), 10000)
    }

    private fun live() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val result = RequestUtil.requestObject(
                    Method.GET, Config.bilibiliLiveV2,
                    requestBody, headers.build(),
                    Config.logger
                )
                val biliBiliLive = JSON.parseObject(result.toString(), BiliBiliLive::class.java)

                if (liveStatus != biliBiliLive.data.liveRoom.liveStatus && biliBiliLive.data.liveRoom.liveStatus == 1) {
                    runBlocking {
                        liveStatus = biliBiliLive.data.liveRoom.liveStatus
                        GroupSender.sendMessage(biliBiliLive)
                        delay(10000L)
                    }
                } else if (liveStatus != biliBiliLive.data.liveRoom.liveStatus && biliBiliLive.data.liveRoom.liveStatus == 0 && liveStatus != -1) {
                    liveStatus = -1
                    runBlocking {
                        GroupSender.sendMessage(biliBiliLive)
                        delay(10000L)
                    }
                }

            }
        }, Date(), 10000)
    }
}