package com.moose.data.models

data class Chat(val id: String, val createdAt: String){
    constructor(): this("", "")
}
