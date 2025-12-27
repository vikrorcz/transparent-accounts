package com.bura.transparent.accounts.data

import com.bura.transparent.accounts.domain.TransparentAccountsRepository
import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.model.TransparentAccount
import com.bura.transparent.network.data.TransparentAccountsApiService
import com.bura.transparent.network.model.TransparentAccountDto
import com.bura.transparent.network.model.TransparentAccountTransactionDto
import java.time.LocalDate

class TransparentAccountsRepositoryImpl(
    private val api: TransparentAccountsApiService,
): TransparentAccountsRepository {

    override suspend fun fetchTransparentAccounts(): Result<List<TransparentAccount>> = runCatching {
        api.getTransparentAccounts()
            .accounts
            .map { it.toDomain() }
    }

    override suspend fun fetchTransparentAccountTransactions(
        accountNumber: AccountNumber,
    ): Result<List<Transaction>> = runCatching {
        api.getTransparentAccountTransactions(id = accountNumber.accountNumber)
            .transactions
            .map { it.toDomain() }
    }

    private fun TransparentAccountDto.toDomain() = TransparentAccount(
        accountNumber = AccountNumber(accountNumber, bankCode),
        name = name,
        totalAmount = Money(balance.toString(), currency.toString()),
    )

    private fun TransparentAccountTransactionDto.toDomain() = Transaction(
        //name = sender?.name,
        //date = LocalDate.parse(processingDate),
        //note = typeDescription,
        name = null,
        date = null,
        note = null,
        money = Money(amount.value.toString(), amount.currency),
    )
}