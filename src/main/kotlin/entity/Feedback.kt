package com.hcyacg.hanayamata.entity

import com.alibaba.fastjson.annotation.JSONField

data class Feedback(
    @JSONField(name = "id")
    val id: Int?,
    @JSONField(name = "content")
    val content: String,
    @JSONField(name = "qqId")
    val qqId: Long,
    @JSONField(name = "groupId")
    val groupId: Long,
    @JSONField(name = "botId")
    val botId: Long,
    @JSONField(name = "createTime")
    val createTime: String,
)