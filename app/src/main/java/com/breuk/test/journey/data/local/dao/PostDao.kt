package com.breuk.test.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.breuk.test.journey.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM postentity")
    suspend fun getPosts(): List<PostEntity>

    @Query("DELETE FROM postentity")
    suspend fun deletePosts()
}