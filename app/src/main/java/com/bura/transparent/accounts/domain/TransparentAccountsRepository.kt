package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.model.TransparentAccount

interface TransparentAccountsRepository {

    suspend fun fetchTransparentAccounts(): Result<List<TransparentAccount>>

    suspend fun fetchTransparentAccountTransactions(accountNumber: AccountNumber): Result<List<Transaction>>
}