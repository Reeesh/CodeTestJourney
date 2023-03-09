package com.breuk.test.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.breuk.test.journey.data.local.entity.CommentEntity
import com.breuk.test.journey.data.local.entity.PostEntity

@Dao
interface JsonPlaceholderDao {
    // Posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM postentity")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM postentity WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity

    @Query("DELETE FROM postentity WHERE id in (:ids)")
    suspend fun deletePosts(ids: List<Int>)

    // Comments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Query("SELECT * FROM commententity WHERE postId = :postId")
    suspend fun getCommentsForPost(postId: Int): List<CommentEntity>

    @Query("DELETE FROM commententity WHERE id in (:ids)")
    suspend fun deleteComments(ids: List<Int>)
}