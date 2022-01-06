package com.hcyacg.hanayamata.dto

import com.alibaba.fastjson.annotation.JSONField


data class Qa(
    @JSONField(name = "content")
    val content: String,
    @JSONField(name = "expression")
    val expression: String,
    @JSONField(name = "html")
    val html: String,
    @JSONField(name = "id")
    val id: Int,
    @JSONField(name = "markdown")
    val markdown: String,
    @JSONField(name = "note")
    val note: String
)