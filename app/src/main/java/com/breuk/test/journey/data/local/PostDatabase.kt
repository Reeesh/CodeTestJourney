package com.breuk.test.journey.data.local

import androidx.room.Database
import com.breuk.test.journey.data.local.dao.PostDao
import com.breuk.test.journey.data.local.entity.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1
)
abstract class PostDatabase {
    abstract val dao: PostDao
}