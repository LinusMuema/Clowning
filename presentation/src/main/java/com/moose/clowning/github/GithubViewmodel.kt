package com.moose.clowning.github

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.domain.models.GithubUser
import com.moose.domain.usecases.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewmodel @Inject constructor(private val getUserInfoUseCase: GetUserInfoUseCase): ViewModel() {

    init {
        getUser()
    }

    private val _user: MutableState<GithubUser?> = mutableStateOf(null)
    val user: State<GithubUser?> = _user

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserInfoUseCase.invoke()
            _user.value = user
        }
    }
}