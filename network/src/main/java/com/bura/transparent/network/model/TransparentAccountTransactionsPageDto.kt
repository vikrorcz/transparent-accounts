package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentAccountTransactionsPageDto(
    val pageNumber: Int,
    val pageSize: Int,
    val pageCount: Int,
    val nextPage: Int? = null,
    val recordCount: Int,
    val transactions: List<TransparentAccountTransactionDto> = emptyList(),
)