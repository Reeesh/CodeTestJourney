package com.breuk.test.journey.domain.useCase

import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: JsonPlaceholderRepository
) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}