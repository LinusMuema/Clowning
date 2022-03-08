package com.moose.data

import com.moose.data.local.PostsDao
import com.moose.data.models.DataPost
import com.moose.data.models.toDomain
import com.moose.data.remote.PostEndpoints
import com.moose.data.repositories.PostRepositoryImpl
import com.moose.domain.repositories.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class PostRepositoryTest {
    private lateinit var postsDao: PostsDao
    private lateinit var postEndpoints: PostEndpoints
    private lateinit var postRepository: PostRepository

    lateinit var dbPost: DataPost
    lateinit var netPost: DataPost

    @Before
    fun setup() {
        // mock the classes
        postsDao = mock()
        postEndpoints = mock()
        postRepository = PostRepositoryImpl(postEndpoints, postsDao)

        // mock the data
        dbPost = DataPost(id = 1, title = "Db post")
        netPost = DataPost(id = 1, title = "Net post")
    }

    @Test
    fun `when given an id, then the correct data is requested`() {
        runBlocking {
            whenever(postsDao.getPostById(any())).thenReturn(dbPost)

            // Given a valid id
            val postId = (0..10).random()

            // When getSinglePost is called
            postRepository.getSinglePost(postId)

            // Then the correct data is requested
            verify(postsDao, times(1)).getPostById(postId)
        }
    }

    @Test
    fun `when post exists in db, then the same post is returned`() {
        runBlocking {
            // Given the posts is in the database
            whenever(postsDao.getPostById(any())).thenReturn(dbPost)

            // When we request the data
            val postId = (0..10).random()
            val result = postRepository.getSinglePost(postId)

            // Then...
            verify(postEndpoints, never()).getSinglePost(any()) // ...no network call is made
            assert(result == dbPost.toDomain()) // ...we should get the same post
        }
    }

    @Test
    fun `when post does not exist in db, then post from a network request is returned and saved in the db`() {
        runBlocking {

            // Give the mock repository, and the posts is not in the database
            whenever(postsDao.getPostById(any())).thenReturn(null)
            whenever(postEndpoints.getSinglePost(any())).thenReturn(Response.success(netPost))

            // When we request the data
            val postId = (0..10).random()
            val result = postRepository.getSinglePost(postId)

            // Then...
            verify(postEndpoints, times(1)).getSinglePost(postId) // ...a network call is made
            verify(postsDao, times(1)).insertPosts(netPost) // ...the post is saved in the db
            assert(result == netPost.toDomain()) // ...we should get the same post
        }
    }
}