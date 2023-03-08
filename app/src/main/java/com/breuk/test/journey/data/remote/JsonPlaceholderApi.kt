package com.breuk.test.journey.data.remote

import com.breuk.test.journey.domain.model.Post
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("https://jsonplaceholder.typicode.com/posts")
    suspend fun getPosts(): List<Post>
}