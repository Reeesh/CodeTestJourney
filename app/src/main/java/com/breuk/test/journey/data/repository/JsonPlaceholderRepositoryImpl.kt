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
    override fun getPost(postId: Int): Flow<Task<Post>> = flow {
        emit(Task.Loading())

        val localPost = dao.getPostById(postId).toPost()
        emit(Task.Loading(data = localPost))

        runCatching {
            api.getPost(postId)
        }.onSuccess { post ->
            dao.deletePosts(listOf(post.id))
            dao.insertPosts(listOf(post.toPostEntity()))
            emit(Task.Success(data = post))
        }.onFailure { error ->
            emit(Task.Error(exception = error, data = localPost))
        }
    }

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

    override fun searchPosts(text: String): Flow<Task<List<Post>>> = flow {
        val posts = if (text.isEmpty()) {
            dao.getPosts().map { it.toPost() }
        } else {
            dao.searchPosts(text).map { it.toPost() }
        }

        emit(Task.Success(data = posts))
    }

    override fun getComments(postId: Int): Flow<Task<List<Comment>>> = flow {
        emit(Task.Loading())

        val localComments = dao.getCommentsFromPost(postId).map { it.toComment() }
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

    override fun searchComments(postId: Int, text: String): Flow<Task<List<Comment>>> = flow {
        val comments = if (text.isEmpty()) {
            dao.getCommentsFromPost(postId).map { it.toComment() }
        } else {
            dao.searchCommentsFromPost(postId, text).map { it.toComment() }
        }

        emit(Task.Success(data = comments))
    }
}