package com.breuk.test.journey.domain.usecase

import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: JsonPlaceholderRepository
) {
    operator fun invoke(): Flow<Task<List<Post>>> {
        return repository.getPosts()
    }
}