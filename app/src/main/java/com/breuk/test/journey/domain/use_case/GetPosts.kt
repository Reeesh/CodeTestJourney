package com.breuk.test.journey.domain.use_case

import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow

class GetPosts(
    private val repository: JsonPlaceholderRepository
) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}