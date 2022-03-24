package com.moose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moose.data.models.GithubUserDto

//@TypeConverters(Converters::class)
@Database(entities = [GithubUserDto::class], version = 1)
abstract class ClowningDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "clowning_database"
    }
}