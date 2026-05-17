package com.example.nobel.data.remote.response

import com.example.nobel.data.model.PrizeDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PrizesResponse (
    val nobelPrizes: List<PrizeDto>
)