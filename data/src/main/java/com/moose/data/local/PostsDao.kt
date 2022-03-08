package com.moose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moose.data.models.PostDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("select * from datapost")
    fun getPosts(): Flow<List<PostDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg postDetails: PostDetails)

    @Query("select * from datapost where id = :id")
    suspend fun getPostById(id: Int): PostDetails?
}