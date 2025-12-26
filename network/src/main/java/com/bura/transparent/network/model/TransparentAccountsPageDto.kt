package com.bura.transparent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransparentAccountsPageDto(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val recordCount: Int,
    val nextPage: Int? = null,
    val accounts: List<TransparentAccountDto>,
)