package com.moose.data.models

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["id"])
data class Post(
    val id: Int,
    val userId: Int = 0,
    val body: String = "",
    val title: String = ""
)