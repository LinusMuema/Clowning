package com.moose.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseService {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): ClowningDatabase {
        return Room.databaseBuilder(context, ClowningDatabase::class.java, ClowningDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: ClowningDatabase) = appDatabase.postsDao()

}