package com.moose.clowning.messages

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.data.models.Message
import com.moose.data.remote.FirebaseService
import kotlinx.coroutines.launch

class MessagesViewModel: ViewModel() {
    val randomUser = (1..5).map { ('a'..'z').random() }.joinToString("")

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error

    private val _messages: MutableState<List<Message?>> = mutableStateOf(emptyList())
    val messages: State<List<Message?>> = _messages

    fun getMessages(chatId: String){
        viewModelScope.launch {
            FirebaseService.getChatMessages(chatId).collect {
                when {
                    it.isSuccess -> _messages.value = it.getOrDefault(emptyList())
                    it.isFailure -> _error.value = it.exceptionOrNull()?.message
                }
            }
        }
    }

    fun sendMessage(chatId: String, value: String){
        val message = Message(text = value, user = randomUser)
        FirebaseService.sendMessage(chatId, message)
    }

}