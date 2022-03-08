package com.moose.data.repositories

import com.moose.data.local.PostsDao
import com.moose.data.models.Post
import com.moose.data.remote.PostEndpoints
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PostRepository {
    val posts: Flow<List<Post>>

    suspend fun getSinglePost(id: Int): Post
}


class PostRepositoryImpl @Inject constructor(val api: PostEndpoints, val postsDao: PostsDao): PostRepository {
    override val posts: Flow<List<Post>>
        get() = postsDao.getPosts()

    override suspend fun getSinglePost(id: Int): Post {
        val dbPost =  postsDao.getPostById(id)
        return if (dbPost != null) {
            dbPost
        } else {
            val netPost = api.getSinglePost(id).body()!!
            postsDao.insertPosts(netPost)
            netPost
        }
    }

}