package com.moose.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moose.data.models.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("select * from post")
    fun getPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(vararg posts: Post)

    @Query("select * from post where id = :id")
    suspend fun getPostById(id: Int): Post?
}