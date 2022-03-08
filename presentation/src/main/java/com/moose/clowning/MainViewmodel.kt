package com.moose.clowning

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(getPostsUseCase: GetPostsUseCase, private val getSinglePostUseCase: GetSinglePostUseCase): ViewModel() {

    val posts: Flow<List<Post>> = getPostsUseCase.invoke()

    private val _post: MutableState<Post?> = mutableStateOf(null)
    val post: State<Post?> = _post

    fun getSinglePost(id: Int) {
        viewModelScope.launch {
            _post.value = getSinglePostUseCase.invoke(id)
        }
    }
}