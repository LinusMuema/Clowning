package com.moose.data.repositories

import com.moose.data.local.PostsDao
import com.moose.data.models.toDomain
import com.moose.data.remote.PostEndpoints
import com.moose.domain.models.Post
import com.moose.domain.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostEndpoints,
    private val postsDao: PostsDao
): PostRepository {

    override suspend fun getPosts(): Flow<List<Post>>{
        val posts = postsDao.getPosts()
        if (posts.first().isEmpty()) {
            val id = (1..10).random()
            val netPost = api.getSinglePost(id).body()!!
            postsDao.insertPosts(netPost)
        }
        return postsDao.getPosts().map { items -> items.map { it.toDomain() } }
    }

    override suspend fun getSinglePost(id: Int): Post {
        val dbPost =  postsDao.getPostById(id)
        return if (dbPost != null) {
            dbPost.toDomain()
        } else {
            val netPost = api.getSinglePost(id).body()!!
            postsDao.insertPosts(netPost)
            netPost.toDomain()
        }
    }
}