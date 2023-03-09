package com.breuk.test.journey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.breuk.test.journey.domain.model.Comment

@Entity
data class CommentEntity(
    @PrimaryKey val id: Int,
    val body: String,
    val email: String,
    val name: String,
    val postId: Int
) {
    fun toComment(): Comment {
        return Comment(
            id = id,
            body = body,
            email = email,
            name = name,
            postId = postId
        )
    }
}