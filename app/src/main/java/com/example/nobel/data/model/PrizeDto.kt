package com.example.nobel.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PrizeDto (
    val awardYear: Int,
    val category: JsonElement,
    val laureates: JsonElement
)