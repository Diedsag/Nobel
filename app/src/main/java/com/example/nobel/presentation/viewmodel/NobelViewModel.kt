package com.example.nobel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nobel.domain.model.Prize
import com.example.nobel.domain.usecase.GetPrizesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NobelViewModel(
    val getPrizesUseCase: GetPrizesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<PrizesListUiState>(PrizesListUiState.Loading)
    val uiState: StateFlow<PrizesListUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PrizesListUiState.Loading

            getPrizesUseCase().onSuccess { prizes ->
                _uiState.value = if (prizes.isEmpty()) {
                    PrizesListUiState.Empty
                } else {
                    PrizesListUiState.Success(prizes)
                }
            }.onFailure { e ->
                _uiState.value = PrizesListUiState.Error(e.message ?: "Ошибка загрузки постов")
            }
        }
    }

    fun filter(year: Int, category: String){
        viewModelScope.launch {
            _uiState.value = PrizesListUiState.Loading

            getPrizesUseCase(year, category).onSuccess { prizes ->
                _uiState.value = if (prizes.isEmpty()) {
                    PrizesListUiState.Empty
                } else {
                    PrizesListUiState.Success(prizes)
                }
            }.onFailure { e ->
                _uiState.value = PrizesListUiState.Error(e.message ?: "Ошибка загрузки постов")
            }
        }
    }
}