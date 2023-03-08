package com.breuk.test.journey.data.repository

import com.breuk.test.journey.data.local.dao.PostDao
import com.breuk.test.journey.data.remote.JsonPlaceholderApi
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JsonPlaceholderRepositoryImpl(
    private val api: JsonPlaceholderApi,
    private val dao: PostDao
) : JsonPlaceholderRepository {

    override fun getPosts(): Flow<List<Post>> = flow {
        emit(emptyList())

        val localPosts = dao.getPosts().map { it.toPost() }
        emit(localPosts)

        runCatching {
            api.getPosts()
        }.onSuccess { posts ->
            dao.deletePosts()
            dao.insertPosts(posts.map { it.toPostEntity() })
            emit(posts)
        }.onFailure {
            //TODO add error handling
        }
    }
}