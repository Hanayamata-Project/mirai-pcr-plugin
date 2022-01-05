package com.hcyacg.hanayamata.entity

import com.alibaba.fastjson.annotation.JSONField


data class BigFunInfo(
    @JSONField(name = "data")
    val data: List<BFData>,
    @JSONField(name = "pagination")
    val pagination: Pagination
)

data class BFData(
    @JSONField(name = "comment_count")
    val commentCount: Int,
    @JSONField(name = "content")
    val content: String,
    @JSONField(name = "display_style")
    val displayStyle: Int,
    @JSONField(name = "display_view_count")
    val displayViewCount: Int,
    @JSONField(name = "forum")
    val forum: Forum,
    @JSONField(name = "id")
    val id: String,
    @JSONField(name = "images")
    val images: List<String>,
    @JSONField(name = "is_fire")
    val isFire: Int,
    @JSONField(name = "is_like")
    val isLike: Int,
    @JSONField(name = "like_count")
    val likeCount: Int,
    @JSONField(name = "post_time")
    val postTime: Int,
    @JSONField(name = "recommend")
    val recommend: Int,
    @JSONField(name = "server_time")
    val serverTime: Int,
    @JSONField(name = "title")
    val title: String,
    @JSONField(name = "topics_struct")
    val topicsStruct: List<TopicsStruct>,
    @JSONField(name = "user")
    val user: User
)

data class Pagination(
    @JSONField(name = "current_page_num")
    val currentPageNum: Int,
    @JSONField(name = "page_limit")
    val pageLimit: Int,
    @JSONField(name = "total_count")
    val totalCount: Int,
    @JSONField(name = "total_page")
    val totalPage: Int
)

data class Forum(
    @JSONField(name = "id")
    val id: String,
    @JSONField(name = "parent_forum_id")
    val parentForumId: String,
    @JSONField(name = "title")
    val title: String
)

data class TopicsStruct(
    @JSONField(name = "name")
    val name: String,
    @JSONField(name = "topic_id")
    val topicId: String
)

data class User(
    @JSONField(name = "avatar")
    val avatar: String,
    @JSONField(name = "id")
    val id: String,
    @JSONField(name = "nickname")
    val nickname: String
)