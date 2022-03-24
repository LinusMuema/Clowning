package com.moose.domain.repositories

import com.moose.domain.models.GithubUser

interface GithubRepository {
    fun addGithubToken(token: String)

    suspend fun getUserInfo(): GithubUser

}