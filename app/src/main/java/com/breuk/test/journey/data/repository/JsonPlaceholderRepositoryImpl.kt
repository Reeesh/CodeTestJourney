package com.breuk.test.journey.data.repository

import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.data.local.dao.JsonPlaceholderDao
import com.breuk.test.journey.data.remote.JsonPlaceholderApi
import com.breuk.test.journey.domain.model.Comment
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JsonPlaceholderRepositoryImpl(
    private val api: JsonPlaceholderApi,
    private val dao: JsonPlaceholderDao
) : JsonPlaceholderRepository {

    override fun getPosts(): Flow<Task<List<Post>>> = flow {
        emit(Task.Loading())

        val localPosts = dao.getPosts().map { it.toPost() }
        emit(Task.Loading(data = localPosts))

        runCatching {
            api.getPosts()
        }.onSuccess { posts ->
            dao.deletePosts(posts.map { it.id })
            dao.insertPosts(posts.map { it.toPostEntity() })
            emit(Task.Success(data = posts))
        }.onFailure { error ->
            emit(Task.Error(exception = error, data = localPosts))
        }
    }

    override fun getComments(postId: Int): Flow<Task<List<Comment>>> = flow {
        emit(Task.Loading())

        val localComments = dao.getCommentsForPost(postId).map { it.toComment() }
        emit(Task.Loading(data = localComments))

        runCatching {
            api.getComments(postId)
        }.onSuccess { comments ->
            dao.deleteComments(comments.map { it.id })
            dao.insertComments(comments.map { it.toCommentEntity() })
            emit(Task.Success(data = comments))
        }.onFailure { error ->
            emit(Task.Error(exception = error, data = localComments))
        }
    }
}