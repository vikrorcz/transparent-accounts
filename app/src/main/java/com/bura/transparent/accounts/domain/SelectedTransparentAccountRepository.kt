package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.model.AccountNumber

interface SelectedTransparentAccountRepository {

    fun store(accountNumber: AccountNumber)

    fun load(): AccountNumber
}