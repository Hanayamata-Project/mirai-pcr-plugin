package com.hcyacg.hanayamata

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.hcyacg.hanayamata.config.Config
import com.hcyacg.hanayamata.entity.*
import com.hcyacg.hanayamata.utils.ImageUtil
import net.mamoe.mirai.Bot
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.util.*

object GroupSender {
    private val groupList = mutableListOf(960879198L, 834014382L)
//    private val groupList = mutableListOf<Long>()

    suspend fun GroupSender.sendMessage(bigFunInfo: BigFunInfo) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach here@{ group ->
                if (groupList.isNotEmpty() && groupList.indexOf(group.id) == -1) {
                    return@here
                }
                run {
                    if (bigFunInfo.data[0].images.isEmpty()) {
                        group.sendMessage(
                            PlainText("${bigFunInfo.data[0].user.nickname} 发表了新文章").plus("\n")
                                .plus("======").plus("\n")
                                .plus(bigFunInfo.data[0].title).plus("\n")
                                .plus(bigFunInfo.data[0].content).plus("\n")
                                .plus("https://www.bigfun.cn/post/${bigFunInfo.data[0].id}")
                        );
                    } else {
                        val toExternalResource =
                            ImageUtil.getImage(bigFunInfo.data[0].images[0]).toByteArray().toExternalResource()
                        val imageId: String = toExternalResource.uploadAsImage(group).imageId
                        toExternalResource.close()
                        group.sendMessage(
                            PlainText("${bigFunInfo.data[0].user.nickname} 发表了新文章").plus("\n")
                                .plus("======").plus("\n")
                                .plus(Image(imageId)).plus("\n")
                                .plus(bigFunInfo.data[0].title).plus("\n")
                                .plus(bigFunInfo.data[0].content).plus("\n")
                                .plus("https://www.bigfun.cn/post/${bigFunInfo.data[0].id}")
                        );
                    }


                }
            }
        }
    }

    suspend fun GroupSender.sendMessage(biliBiliArticle: BiliBiliArticle) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach here@{ group ->
                if (groupList.size != 0 && groupList.indexOf(group.id) == -1) {
                    return@here
                }
                run {
                    val toExternalResource =
                        ImageUtil.getImage(biliBiliArticle.data.articles!![0].bannerUrl).toByteArray()
                            .toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    group.sendMessage(
                        PlainText("${biliBiliArticle.data.articles[0].author.name} 发表了新专栏文章").plus("\n")
                            .plus("======").plus("\n")
                            .plus(Image(imageId)).plus("\n")
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
            bot.groups.forEach here@{ group ->
                if (groupList.size != 0 && groupList.indexOf(group.id) == -1) {
                    return@here
                }
                run {
                    val toExternalResource =
                        ImageUtil.getImage(biliBiliVideo.data.list.vlist!![0].pic).toByteArray().toExternalResource()
                    val imageId: String = toExternalResource.uploadAsImage(group).imageId
                    toExternalResource.close()
                    group.sendMessage(
                        PlainText("${biliBiliVideo.data.list.vlist[0].author} 发表了新视频").plus("\n")
                            .plus("======").plus("\n")
                            .plus(Image(imageId)).plus("\n")
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
            bot.groups.forEach here@{ group ->
                if (groupList.size != 0 && groupList.indexOf(group.id) == -1) {
                    return@here
                }

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


    suspend fun GroupSender.sendMessage(biliBiliDynamic: BiliBiliDynamic) {
        Bot.instances.forEach { bot ->
            bot.groups.forEach here@{ group ->
                if (groupList.size != 0 && groupList.indexOf(group.id) == -1) {
                    return@here
                }

                run {
                    val biliBiliDynamicItem = JSON.parseObject(
                        JSONObject.parseObject(biliBiliDynamic.data.cards[0].card).toString(),
                        BiliBiliDynamicItem::class.java
                    )
                    val imageList = mutableListOf<String>()
                    biliBiliDynamicItem.item?.pictures?.forEach {
                        val toExternalResource =
                            ImageUtil.getImage(it.imgSrc).toByteArray().toExternalResource()
                        val imageId: String = toExternalResource.uploadAsImage(group).imageId
                        imageList.add(imageId)
                        toExternalResource.close()
                    }

                    var message: MessageChain = PlainText("${biliBiliDynamicItem.user?.name}B站动态更新了".plus("\n"))
                        .plus("======").plus("\n")
                        .plus(biliBiliDynamicItem.item!!.description).plus("\n")

                    imageList.forEach {
                        message = message.plus(Image(it)).plus("\n")
                    }
                    message = message.plus("https://t.bilibili.com/${biliBiliDynamic.data.cards[0].desc.dynamicIdStr}")
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