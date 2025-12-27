package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PartyDto(
    val accountNumber: String? = null,
    val bankCode: String? = null,
    val iban: String? = null,
    val specificSymbol: String? = null,
    val specificSymbolParty: String? = null,
    val variableSymbol: String? = null,
    val constantSymbol: String? = null,
    val name: String? = null,
    val description: String? = null,
)
