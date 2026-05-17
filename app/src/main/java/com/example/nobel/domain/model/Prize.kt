package com.example.nobel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Prize (
    val year: Int,
    val category: String,
    val fullNames: List<String>,
    val motivations: List<String>
)