package com.moose.domain.usecases

import com.moose.domain.repositories.GithubRepository
import javax.inject.Inject

class SaveGithubTokenUseCase @Inject constructor(private val repository: GithubRepository) {
    operator fun invoke(token: String) = repository.addGithubToken(token)
}