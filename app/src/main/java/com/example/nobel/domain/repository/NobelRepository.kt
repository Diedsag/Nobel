package com.example.nobel.domain.repository

import com.example.nobel.domain.model.Prize

interface NobelRepository {
    suspend fun getPrizes(): Result<List<Prize>>
    suspend fun getFiltered(year: Int, category: String): Result<List<Prize>>
}