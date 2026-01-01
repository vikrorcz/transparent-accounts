package com.bura.transparent.accounts

import com.bura.transparent.accounts.data.SelectedTransparentAccountRepositoryImpl
import com.bura.transparent.accounts.domain.SelectedTransparentAccountRepository
import com.bura.transparent.accounts.domain.TransparentAccountsRepository
import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.model.TransparentAccount

internal fun transparentAccountsRepository(
    accounts: List<TransparentAccount> = emptyList(),
    transactions: List<Transaction> = emptyList(),
) = object : TransparentAccountsRepository {
    override suspend fun fetchTransparentAccounts() = Result.success(accounts)

    override suspend fun fetchTransparentAccountTransactions(accountNumber: AccountNumber) = Result.success(transactions)
}

internal fun transparentAccount(
    accountNumber: AccountNumber = AccountNumber("12345", "1234"),
    name: String = "name",
    totalAmount: Money = Money("1000", "CZK"),
) = TransparentAccount(
    accountNumber = accountNumber,
    name = name,
    totalAmount = totalAmount,
)

internal fun transaction(
    name: String = "name",
    note: String = "note",
    money: Money = Money("1000", "CZK"),
) = Transaction(
    name = name,
    note = note,
    money = money,
)

internal fun selectedTransparentAccountRepository(
    selected: TransparentAccount = transparentAccount(),
) = SelectedTransparentAccountRepositoryImpl().apply { store(selected) }

internal fun fetchAccountsUseCase(
    repository: TransparentAccountsRepository = transparentAccountsRepository(),
) = TransparentAccountsUseCase.FetchAccounts(
    repository = repository,
)

internal fun fetchAccountsSuccess(
    accounts: List<TransparentAccount>,
): Result<List<TransparentAccount>> = Result.success(accounts)

internal fun fetchTransactionsSuccess(
    transactions: List<Transaction>,
): Result<List<Transaction>> = Result.success(transactions)

internal fun fetchOverviewUseCase(
    repository: TransparentAccountsRepository = transparentAccountsRepository(),
    selectedAccountRepository: SelectedTransparentAccountRepository = selectedTransparentAccountRepository(),
) = TransparentAccountsUseCase.FetchOverview(
    repository = repository,
    selectedTransparentAccountRepository = selectedAccountRepository,
)

internal fun loadSelectedUseCase(
    selectedAccountRepository: SelectedTransparentAccountRepository = selectedTransparentAccountRepository(),
) = TransparentAccountsUseCase.LoadSelected(
    selectedTransparentAccountRepository = selectedAccountRepository,
)
