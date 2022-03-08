package com.moose.domain.repositories

import com.moose.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val posts: Flow<List<Post>>

    suspend fun getSinglePost(id: Int): Post
}