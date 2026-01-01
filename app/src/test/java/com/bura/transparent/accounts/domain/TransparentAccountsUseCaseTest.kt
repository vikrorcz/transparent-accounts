package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.fetchAccountsSuccess
import com.bura.transparent.accounts.fetchAccountsUseCase
import com.bura.transparent.accounts.fetchOverviewUseCase
import com.bura.transparent.accounts.fetchTransactionsSuccess
import com.bura.transparent.accounts.loadSelectedUseCase
import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.selectedTransparentAccountRepository
import com.bura.transparent.accounts.transaction
import com.bura.transparent.accounts.transparentAccount
import com.bura.transparent.accounts.transparentAccountsRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TransparentAccountsUseCaseTest {

    @Test
    fun `fetch accounts should return result of repository`() = runTest {
        val accounts = listOf(
            transparentAccount(
                accountNumber = AccountNumber("0001", "1234"),
                name = "first",
            ),
            transparentAccount(
                accountNumber = AccountNumber("0002", "1234"),
                name = "second",
            ),
            transparentAccount(
                accountNumber = AccountNumber("0003", "1234"),
                name = "third",
            ),
        )
        val result = fetchAccountsSuccess(accounts)
        val repository = transparentAccountsRepository(accounts = accounts)
        val fetch = fetchAccountsUseCase(repository = repository)

        fetch() shouldBe result
    }

    @Test
    fun `fetch overview should return result of repository`() = runTest {
        val transactions = listOf(
            transaction(name = "first"),
            transaction(name = "second"),
            transaction(name = "third"),
        )
        val selectedAccount = transparentAccount(name = "second")
        val result = fetchTransactionsSuccess(transactions)
        val repository = transparentAccountsRepository(transactions = transactions)
        val selectedRepository = selectedTransparentAccountRepository(selectedAccount)
        val fetch = fetchOverviewUseCase(
            repository = repository,
            selectedAccountRepository = selectedRepository,
        )

        fetch() shouldBe result
    }

    @Test
    fun `should load selected transparent account`() = runTest {
        val selectedAccount = transparentAccount(name = "second")
        val selectedRepository = selectedTransparentAccountRepository(selectedAccount)
        val load = loadSelectedUseCase(selectedAccountRepository = selectedRepository)

        load() shouldBe selectedAccount
    }
}