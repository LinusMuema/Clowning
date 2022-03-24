package com.moose.clowning.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moose.domain.usecases.SaveGithubTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val saveGithubTokenUseCase: SaveGithubTokenUseCase
): ViewModel() {

    fun setToken(token: String) = saveGithubTokenUseCase.invoke(token)

}