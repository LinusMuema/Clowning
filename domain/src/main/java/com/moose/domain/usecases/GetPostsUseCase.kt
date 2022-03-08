package com.moose.domain.usecases

import com.moose.data.repositories.PostRepository
import com.moose.domain.models.Post
import com.moose.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(): Flow<List<Post>> {
        return postRepository.posts.map { posts -> posts.map(::toDomain) }
    }
}