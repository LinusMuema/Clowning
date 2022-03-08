package com.moose.data.remote

import com.moose.data.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostEndpoints {

    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/posts/{id}")
    suspend fun getSinglePost(@Path("id") id: Int): Response<Post>
}