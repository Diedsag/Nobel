package com.example.nobel.presentation.viewmodel

import com.example.nobel.domain.model.Prize

sealed class PrizesListUiState {
    object Loading : PrizesListUiState()
    data class Success(val prizes: List<Prize>) : PrizesListUiState()
    data class Error(val message: String) : PrizesListUiState()
    object Empty : PrizesListUiState()
}