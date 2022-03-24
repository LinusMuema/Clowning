package com.moose.domain.usecases

import com.moose.domain.models.GithubUser
import com.moose.domain.repositories.GithubRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(): GithubUser {
       return githubRepository.getUserInfo()
    }
}