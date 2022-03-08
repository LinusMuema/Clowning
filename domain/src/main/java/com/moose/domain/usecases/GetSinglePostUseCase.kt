package com.moose.domain.usecases

import com.moose.data.repositories.PostRepository
import com.moose.domain.models.Post
import com.moose.domain.models.toDomain
import javax.inject.Inject

class GetSinglePostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Int): Post {
        return toDomain(postRepository.getSinglePost(postId))
    }
}