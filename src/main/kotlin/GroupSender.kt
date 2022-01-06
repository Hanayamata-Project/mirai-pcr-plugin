package com.hcyacg.hanayamata

import com.hcyacg.hanayamata.entity.BigFunInfo
import com.hcyacg.hanayamata.entity.BiliBiliArticle
import com.hcyacg.hanayamata.entity.BiliBiliLive
import com.hcyacg.hanayamata.entity.BiliBiliVideo
import com.hcyacg.hanayamata.utils.ImageUtil
import net.mamoe.mirai.Bot
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.QuoteReply
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage

object GroupSender {

    suspend fun GroupSender.sendMessage(bigFunInfo: BigFunInfo) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
//                if (group.id != 960879198L && group.id != 834014382L) {
//                    return@forEach
//                }
                run {
                    val toExternalResource =
                        ImageUtil.getImage(bigFunInfo.data[0].images[0]).toByteArray().toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    group.sendMessage(
                        Image(imageId).plus("\n")
                            .plus(bigFunInfo.data[0].title).plus("\n")
                            .plus(bigFunInfo.data[0].content).plus("\n")
                            .plus("https://www.bigfun.cn/post/${bigFunInfo.data[0].id}")
                    );
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(biliBiliArticle: BiliBiliArticle) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
//                if (group.id != 960879198L && group.id != 834014382L) {
//                    return@forEach
//                }
                run {
                    val toExternalResource =
                        ImageUtil.getImage(biliBiliArticle.data.articles!![0].bannerUrl).toByteArray()
                            .toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    group.sendMessage(
                        Image(imageId).plus("\n")
                            .plus(biliBiliArticle.data.articles[0].title).plus("\n")
                            .plus(biliBiliArticle.data.articles[0].summary).plus("\n")
                            .plus("https://www.bilibili.com/read/cv${biliBiliArticle.data.articles[0].id}")
                    );
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(biliBiliVideo: BiliBiliVideo) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
//                if (group.id != 960879198L && group.id != 834014382L) {
//                    return@forEach
//                }
                run {
                    val toExternalResource =
                        ImageUtil.getImage(biliBiliVideo.data.list.vlist!![0].pic).toByteArray().toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    group.sendMessage(
                        Image(imageId).plus("\n")
                            .plus(biliBiliVideo.data.list.vlist[0].title).plus("\n")
                            .plus(biliBiliVideo.data.list.vlist[0].description).plus("\n")
                            .plus("https://www.bilibili.com/video/${biliBiliVideo.data.list.vlist[0].bvid}")
                    );
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(biliBiliLive: BiliBiliLive) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
//                if (group.id != 960879198L && group.id != 834014382L) {
//                    return@forEach
//                }

                run {
                    val toExternalResource =
                        ImageUtil.getImage(biliBiliLive.data.liveRoom.cover).toByteArray().toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    var message: Message = PlainText(biliBiliLive.data.name)
                    if (biliBiliLive.data.liveRoom.liveStatus == 0) {
                        message = message.plus("下播了")
                    } else if (biliBiliLive.data.liveRoom.liveStatus == 1) {
                        message = message.plus("开播了")
                            .plus(Image(imageId)).plus("\n")
                            .plus(biliBiliLive.data.liveRoom.title).plus("\n")
                            .plus(biliBiliLive.data.liveRoom.url)
                    }
                    group.sendMessage(message)
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(message: String) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
                run {
                    group.sendMessage(message);
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(message: Message) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
                run {
                    group.sendMessage(message);
                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(message: QuoteReply) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach { group ->
                run {
                    group.sendMessage(message);
                }
            }
        }
    }

}