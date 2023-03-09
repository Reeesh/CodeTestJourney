package com.breuk.test.journey.domain.repository

import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.util.Task
import kotlinx.coroutines.flow.Flow

interface JsonPlaceholderRepository {
    fun getPosts(): Flow<Task<List<Post>>>
}