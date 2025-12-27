package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentAccountTransactionDto(
    val amount: AmountDto,
    val type: String,
    val dueDate: String,
    val processingDate: String,
    val typeDescription: String? = null,
    val sender: PartyDto? = null,
    val receiver: PartyDto? = null,
)
