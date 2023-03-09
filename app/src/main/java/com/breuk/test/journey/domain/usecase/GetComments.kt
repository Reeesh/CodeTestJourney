package com.breuk.test.journey.domain.usecase

import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Comment
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComments @Inject constructor(
    private val repository: JsonPlaceholderRepository
) {
    operator fun invoke(postId: Int): Flow<Task<List<Comment>>> {
        return repository.getComments(postId)
    }
}