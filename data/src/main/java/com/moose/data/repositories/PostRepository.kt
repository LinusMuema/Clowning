package com.moose.data.repositories

import com.moose.data.local.PostsDao
import com.moose.data.models.DataPost
import com.moose.data.remote.PostEndpoints
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PostRepository {
    val posts: Flow<List<DataPost>>

    suspend fun getSinglePost(id: Int): DataPost
}


class PostRepositoryImpl @Inject constructor(
    private val api: PostEndpoints,
    private val postsDao: PostsDao
): PostRepository {

    override val posts: Flow<List<DataPost>>
        get() = postsDao.getPosts()

    override suspend fun getSinglePost(id: Int): DataPost {
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