package com.example.nobel.domain.usecase

import com.example.nobel.domain.model.Prize
import com.example.nobel.domain.repository.NobelRepository
import kotlinx.coroutines.flow.Flow

class GetPrizesUseCase(private val repository: NobelRepository) {
    suspend operator fun invoke(): Result<List<Prize>> = repository.getPrizes()
    suspend operator fun invoke(year: Int, category: String): Result<List<Prize>> = repository.getFiltered(year, category)
}