package com.moose.data.models

data class Message(val text: String, val user: String, val resource: String = ""){
    constructor(): this("", "", "")
}
