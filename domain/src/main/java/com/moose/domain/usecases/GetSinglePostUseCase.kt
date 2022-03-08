package com.moose.domain.usecases

import com.moose.domain.models.Post
import com.moose.domain.repositories.PostRepository
import javax.inject.Inject

class GetSinglePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Int): Post {
        return postRepository.getSinglePost(postId)
    }
}