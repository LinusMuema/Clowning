package com.moose.data.models

import androidx.room.Entity
import com.moose.domain.models.Post
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["id"])
data class PostDetails(
    val id: Int,
    val userId: Int = 0,
    val body: String = "",
    val title: String = ""
)

fun PostDetails.toDomain() = Post(id = id, user = userId, body = body, heading = title)