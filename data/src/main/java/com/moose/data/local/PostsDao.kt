package com.moose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moose.data.models.DataPost
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("select * from post")
    fun getPosts(): Flow<List<DataPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: DataPost)

    @Query("select * from post where id = :id")
    suspend fun getPostById(id: Int): DataPost?
}