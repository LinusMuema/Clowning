package com.moose.data.local

import androidx.room.TypeConverter
import com.moose.data.models.PostDetails
import com.moose.domain.models.Post
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun postsToJson(posts: List<PostDetails>): String = Json.encodeToString(posts)

    @TypeConverter
    fun postsFromJson(json: String): List<PostDetails> = Json.decodeFromString(json)
}