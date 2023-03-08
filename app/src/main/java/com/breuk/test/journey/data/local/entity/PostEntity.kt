package com.breuk.test.journey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.breuk.test.journey.domain.model.Post

@Entity
data class PostEntity(
    @PrimaryKey val id: Int,
    val body: String,
    val title: String,
    val userId: Int
) {
    fun toPost(): Post {
        return Post(
            id = id,
            body = body,
            title = title,
            userId = userId
        )
    }
}
