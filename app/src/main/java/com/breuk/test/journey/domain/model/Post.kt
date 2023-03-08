package com.breuk.test.journey.domain.model


import com.breuk.test.journey.data.local.entity.PostEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "userId")
    val userId: Int
) {
    fun toPostEntity(): PostEntity {
        return PostEntity(
            id = id,
            title = title,
            body = body,
            userId = userId
        )
    }
}