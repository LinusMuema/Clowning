package com.moose.data

import com.moose.data.local.PostsDao
import com.moose.data.models.Post
import com.moose.data.remote.PostEndpoints
import com.moose.data.repositories.PostRepository
import com.moose.data.repositories.PostRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class PostRepositoryTest {
    lateinit var postsDao: PostsDao
    lateinit var postEndpoints: PostEndpoints
    lateinit var postRepository: PostRepository

    lateinit var dbPost: Post
    lateinit var netPost: Post

    @Before
    fun setup() {
        // mock the classes
        postsDao = mock()
        postEndpoints = mock()
        postRepository = PostRepositoryImpl(postEndpoints, postsDao)

        // mock the data
        dbPost = Post(id = 1, title = "Db post")
        netPost = Post(id = 1, title = "Net post")
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
    fun `when post exists in db, then getSinglePost returns it`() {
        runBlocking {
            // Given the posts is in the database
            whenever(postsDao.getPostById(any())).thenReturn(dbPost)

            // When we request the data
            val postId = (0..10).random()
            val result = postRepository.getSinglePost(postId)

            // Then we should get the same post
            verify(postEndpoints, never()).getSinglePost(any())
            verify(postsDao, times(1)).getPostById(postId)
            assert(result == dbPost)
        }
    }

    @Test
    fun `when post does not exist in db, then post from a network request is returned`() {
        runBlocking {

            // Give the mock repository, and the posts is not in the database
            whenever(postsDao.getPostById(any())).thenReturn(null)
            whenever(postEndpoints.getSinglePost(any())).thenReturn(Response.success(netPost))

            // When we request the data
            val postId = (0..10).random()
            val result = postRepository.getSinglePost(postId)

            // Then we should get the same post
            verify(postEndpoints, times(1)).getSinglePost(postId)
            assert(result == netPost)
        }
    }
}