package com.breuk.test.journey.domain.usecase

import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPosts @Inject constructor(
    private val repository: JsonPlaceholderRepository
) {
    operator fun invoke(text: String): Flow<Task<List<Post>>> {
        return repository.searchPosts(text)
    }
}