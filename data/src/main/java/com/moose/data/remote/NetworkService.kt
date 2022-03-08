package com.moose.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.MediaType.Companion.toMediaType
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
object NetworkService {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // serialization setup
    private val json = Json { ignoreUnknownKeys = true }
    private val converter = json.asConverterFactory("application/json".toMediaType())

    // Okhttp client
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun providePostsApi(client: OkHttpClient): PostEndpoints {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .build()

        return retrofit.create(PostEndpoints::class.java)

    }
}