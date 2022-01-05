package com.hcyacg.hanayamata.config

import net.mamoe.mirai.utils.MiraiLogger

object Config {
    val bigFun = "https://www.bigfun.cn/api/client/web?method=getUserPostList&user_id=22532&page=1"
    val bilibiliId = 6750667
    val bilibiliVideo = "https://api.bilibili.com/x/space/arc/search?mid=$bilibiliId&pn=1&ps=25&index=1&jsonp=jsonp"
    val bilibiliArticle =
        "https://api.bilibili.com/x/space/article?mid=$bilibiliId&pn=1&ps=12&sort=publish_time&jsonp=jsonp"
    val bilibiliLive = "https://api.live.bilibili.com/room/v1/Room/room_init?id=22259479"
    val bilibiliLiveV2 = "https://api.bilibili.com/x/space/acc/info?mid=$bilibiliId&jsonp=jsonp"
    val logger = MiraiLogger.Factory.create(this::class.java)
}