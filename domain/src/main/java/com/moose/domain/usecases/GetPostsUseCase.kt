package com.moose.domain.usecases

import com.moose.domain.models.Post
import com.moose.domain.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(): Flow<List<Post>> {
        return postRepository.posts
    }
}