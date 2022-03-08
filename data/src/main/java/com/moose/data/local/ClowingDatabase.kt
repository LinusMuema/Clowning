package com.moose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moose.data.models.DataPost

@Database(entities = [DataPost::class], version = 1)
abstract class ClowningDatabase: RoomDatabase() {

    abstract fun postsDao(): PostsDao

    companion object {
        const val DB_NAME = "clowning_database"
    }
}