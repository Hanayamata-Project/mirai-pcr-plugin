package com.hcyacg.hanayamata.config

import net.mamoe.mirai.utils.MiraiLogger

object Config {
    //22532
    const val bigFun = "https://www.bigfun.cn/api/client/web?method=getUserPostList&user_id=22532&page=1"
    const val bilibiliId = 549739

    //    val bilibiliId = 6750667
    const val bilibiliVideo =
        "https://api.bilibili.com/x/space/arc/search?mid=$bilibiliId&pn=1&ps=25&index=1&jsonp=jsonp"
    const val bilibiliArticle =
        "https://api.bilibili.com/x/space/article?mid=$bilibiliId&pn=1&ps=12&sort=publish_time&jsonp=jsonp"
    const val bilibiliLive = "https://api.live.bilibili.com/room/v1/Room/room_init?id=22259479"
    const val bilibiliLiveV2 = "https://api.bilibili.com/x/space/acc/info?mid=$bilibiliId&jsonp=jsonp"
    const val dynamic = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history"

    val logger = MiraiLogger.Factory.create(this::class.java)
}