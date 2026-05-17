package com.example.nobel.data.repository

import android.util.Log
import com.example.nobel.data.model.PrizeDto
import com.example.nobel.data.remote.KtorClient
import com.example.nobel.data.remote.response.PrizesResponse
import com.example.nobel.domain.model.Prize
import com.example.nobel.domain.repository.NobelRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDateTime

class NobelRepositoryImpl(
): NobelRepository {
    override suspend fun getPrizes(): Result<List<Prize>>  = runCatching {
        val response: PrizesResponse = KtorClient.client.get("nobelPrizes?limit=25&offset=0").body()
        response.nobelPrizes.map { it -> it.toDomain() }
    }
    override suspend fun getFiltered(year: Int, category: String): Result<List<Prize>> = runCatching {
        val response: PrizesResponse = KtorClient.client.get("nobelPrizes?limit=25&offset=0&nobelPrizeYear=$year&nobelPrizeCategory=${category.take(3)}").body()
        response.nobelPrizes.map { it -> it.toDomain() }
    }
}

fun PrizeDto.toDomain() = Prize(
    year = awardYear,
    category = category.jsonObject["en"]?.jsonPrimitive?.content ?: "no category",
    fullNames = laureates.jsonArray.map {it ->
        it.jsonObject["fullName"]?.jsonObject["en"]?.jsonPrimitive?.content ?: "no name"
    },
    motivations = laureates.jsonArray.map { it ->
        it.jsonObject["motivation"]?.jsonObject["en"]?.jsonPrimitive?.content ?: "no motivation"
    }
)