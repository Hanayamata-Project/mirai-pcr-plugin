package com.hcyacg.hanayamata.entity

import com.alibaba.fastjson.annotation.JSONField


data class BiliBiliDynamicItem(
    @JSONField(name = "item")
    val item: Item? = Item(),
    @JSONField(name = "user")
    val user: BDIUser? = BDIUser()
)

data class Item(
    @JSONField(name = "at_control")
    val atControl: String = "",
    @JSONField(name = "category")
    val category: String = "",
    @JSONField(name = "description")
    val description: String = "",
    @JSONField(name = "id")
    val id: Int = 0,
    @JSONField(name = "is_fav")
    val isFav: Int = 0,
    @JSONField(name = "pictures")
    val pictures: List<Picture> = listOf(),
    @JSONField(name = "pictures_count")
    val picturesCount: Int = 0,
    @JSONField(name = "reply")
    val reply: Int = 0,
    @JSONField(name = "role")
    val role: List<Any> = listOf(),
    @JSONField(name = "settings")
    val settings: Settings? = Settings(),
    @JSONField(name = "source")
    val source: List<Any> = listOf(),
    @JSONField(name = "title")
    val title: String = "",
    @JSONField(name = "upload_time")
    val uploadTime: Int = 0
)

data class BDIUser(
    @JSONField(name = "head_url")
    val headUrl: String = "",
    @JSONField(name = "name")
    val name: String = "",
    @JSONField(name = "uid")
    val uid: Int = 0,
    @JSONField(name = "vip")
    val vip: BDIVip? = BDIVip()
)

data class Picture(
    @JSONField(name = "img_height")
    val imgHeight: Int = 0,
    @JSONField(name = "img_size")
    val imgSize: Double = 0.0,
    @JSONField(name = "img_src")
    val imgSrc: String = "",
    @JSONField(name = "img_width")
    val imgWidth: Int = 0
)

data class Settings(
    @JSONField(name = "copy_forbidden")
    val copyForbidden: String = ""
)

data class BDIVip(
    @JSONField(name = "avatar_subscript")
    val avatarSubscript: Int = 0,
    @JSONField(name = "due_date")
    val dueDate: Long = 0,
    @JSONField(name = "label")
    val label: BDILabel? = BDILabel(),
    @JSONField(name = "nickname_color")
    val nicknameColor: String = "",
    @JSONField(name = "status")
    val status: Int = 0,
    @JSONField(name = "theme_type")
    val themeType: Int = 0,
    @JSONField(name = "type")
    val type: Int = 0,
    @JSONField(name = "vip_pay_type")
    val vipPayType: Int = 0
)

data class BDILabel(
    @JSONField(name = "label_theme")
    val labelTheme: String = "",
    @JSONField(name = "path")
    val path: String = "",
    @JSONField(name = "text")
    val text: String = ""
)