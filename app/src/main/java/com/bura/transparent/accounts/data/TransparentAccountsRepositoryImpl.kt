package com.bura.transparent.accounts.data

import com.bura.transparent.accounts.domain.TransparentAccountsRepository
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.model.TransparentAccount
import com.bura.transparent.network.data.TransparentAccountsApiService
import com.bura.transparent.network.model.TransparentAccountDto

class TransparentAccountsRepositoryImpl(
    private val api: TransparentAccountsApiService,
): TransparentAccountsRepository {

    override suspend fun fetchTransparentAccounts(): Result<List<TransparentAccount>> = runCatching {
        api.getTransparentAccounts()
            .accounts
            .map { it.toDomain() }
    }

    private fun TransparentAccountDto.toDomain() = TransparentAccount(
        name = name,
        totalAmount = Money(balance.toString(), currency.toString()),
    )
}