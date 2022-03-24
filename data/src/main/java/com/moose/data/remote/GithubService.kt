package com.moose.data.remote

import com.moose.data.local.Preferences
import com.moose.data.models.GithubUserDto
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json as serializer
import javax.inject.Inject

class GithubService @Inject constructor(private val preferences: Preferences){

    private val client = HttpClient {
        val token = preferences.getGithubToken()
        defaultRequest {
            header("Authorization", "token $token")
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(serializer {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getProfile(): GithubUserDto {
        return client.get("https://api.github.com/user")
    }
}