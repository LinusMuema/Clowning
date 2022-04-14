package com.moose.clowning.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.data.models.Chat
import com.moose.data.remote.FirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewmodel : ViewModel() {

    init {
        getChats()
    }

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error

    private val _chats: MutableState<List<Chat?>> = mutableStateOf(emptyList())
    val chats: State<List<Chat?>> = _chats

    fun createChat(): String {
        return FirebaseService.createChat()
    }

    private fun getChats(){
        viewModelScope.launch {
            FirebaseService.getChats().collect {
                when {
                    it.isSuccess -> _chats.value = it.getOrDefault(emptyList())
                    it.isFailure -> _error.value = it.exceptionOrNull()?.message
                }
            }
        }
    }
}