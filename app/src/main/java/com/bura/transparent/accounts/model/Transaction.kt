package com.bura.transparent.accounts.model

import java.time.LocalDate

data class Transaction(
    val name: String?,
    val date: LocalDate?,
    val note: String?,
    val money: Money,
)