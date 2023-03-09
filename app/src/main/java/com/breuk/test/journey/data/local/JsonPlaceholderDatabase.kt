package com.breuk.test.journey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.breuk.test.journey.data.local.dao.JsonPlaceholderDao
import com.breuk.test.journey.data.local.entity.CommentEntity
import com.breuk.test.journey.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class, CommentEntity::class],
    version = 3
)
abstract class JsonPlaceholderDatabase : RoomDatabase() {
    abstract val dao: JsonPlaceholderDao
}