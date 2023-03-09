package com.breuk.test.journey.domain.repository

import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Comment
import com.breuk.test.journey.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface JsonPlaceholderRepository {
    fun getPosts(): Flow<Task<List<Post>>>
    fun getComments(postId: Int): Flow<Task<List<Comment>>>
}