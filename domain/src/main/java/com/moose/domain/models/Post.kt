package com.moose.domain.models

import com.moose.data.models.Post as model

data class Post(val id: Int, val user: Int, val body: String, val heading: String)

fun toDomain(model: model): Post {
    return Post(model.id, model.userId, model.body, model.title)
}

