package com.bura.transparent.accounts.model

data class TransparentAccount(
    val accountNumber: AccountNumber,
    val name: String?,
    val totalAmount: Money,
)