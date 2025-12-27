package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AmountDto(
    val value: Double,
    val precision: Int,
    val currency: String,
)
