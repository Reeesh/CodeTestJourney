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

    @Query("SELECT * FROM postentity WHERE body LIKE :text OR title LIKE :text")
    suspend fun searchPosts(text: String): List<PostEntity>

    @Query("SELECT * FROM postentity WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity

    @Query("DELETE FROM postentity WHERE id in (:ids)")
    suspend fun deletePosts(ids: List<Int>)

    // Comments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Query("SELECT * FROM commententity WHERE postId = :postId")
    suspend fun getCommentsFromPost(postId: Int): List<CommentEntity>

    @Query("SELECT * FROM commententity WHERE postId = :postId AND (body LIKE :text OR name LIKE :text OR email LIKE :text)")
    suspend fun searchCommentsFromPost(postId: Int, text: String): List<CommentEntity>

    @Query("DELETE FROM commententity WHERE id in (:ids)")
    suspend fun deleteComments(ids: List<Int>)
}