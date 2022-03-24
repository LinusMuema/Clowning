package com.moose.data.repositories

import com.moose.data.local.Preferences
import com.moose.data.models.toDomain
import com.moose.data.remote.GithubService
import com.moose.domain.models.GithubUser
import com.moose.domain.repositories.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val preferences: Preferences,
    private val githubService: GithubService
): GithubRepository {
    override fun addGithubToken(token: String) {
        preferences.setGithubToken(token)
    }

    override suspend fun getUserInfo(): GithubUser {
        return githubService.getProfile().toDomain()
    }
}