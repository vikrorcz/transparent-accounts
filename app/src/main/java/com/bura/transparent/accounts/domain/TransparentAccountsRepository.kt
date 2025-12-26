package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.model.TransparentAccount

interface TransparentAccountsRepository {

    suspend fun fetchTransparentAccounts(): Result<List<TransparentAccount>>
}