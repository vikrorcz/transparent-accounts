package com.bura.transparent.accounts.model

data class Transaction(
    val name: String?,
    val note: String?,
    val money: Money,
)