package com.breuk.test.journey.domain.model


import com.breuk.test.journey.data.local.entity.CommentEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    @Json(name = "id")
    val id: Int,
    @Json(name = "body")
    val body: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "postId")
    val postId: Int
) {
    fun toCommentEntity(): CommentEntity {
        return CommentEntity(
            id = id,
            body = body,
            email = email,
            name = name,
            postId = postId
        )
    }
}