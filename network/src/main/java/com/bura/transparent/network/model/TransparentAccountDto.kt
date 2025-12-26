package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentAccountDto(
    val accountNumber: String,
    val bankCode: String,
    val transparencyFrom: String,
    val transparencyTo: String,
    val publicationTo: String,
    val actualizationDate: String,
    val balance: Double,
    val currency: String? = null,
    val name: String,
    val description: String? = null,
    val note: String? = null,
    val iban: String,
    val statements: List<String> = emptyList(),
)