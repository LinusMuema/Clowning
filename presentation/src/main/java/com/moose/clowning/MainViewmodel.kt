package com.moose.clowning

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.domain.models.Post
import com.moose.domain.usecases.GetPostsUseCase
import com.moose.domain.usecases.GetSinglePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(private val getPostsUseCase: GetPostsUseCase): ViewModel() {

    init {
        getPosts()
    }

    private val _posts: MutableState<List<Post>> = mutableStateOf(listOf())
    val posts: State<List<Post>> = _posts

    private fun getPosts(){
        viewModelScope.launch {
            getPostsUseCase.invoke().collect {
                _posts.value = it
            }
        }
    }
}