package com.breuk.test.journey.data.remote

import com.breuk.test.journey.domain.model.Post
import retrofit2.http.GET

interface JsonPlaceholderApi {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("https://jsonplaceholder.typicode.com/posts")
    suspend fun getPosts(): List<Post>
}