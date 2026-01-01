package com.bura.transparent.accounts.scene

import com.bura.transparent.accounts.domain.SelectedTransparentAccountRepository
import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.fetchAccountsUseCase
import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.scene.TransparentAccountsChooseViewModel.State
import com.bura.transparent.accounts.transparentAccount
import com.bura.transparent.accounts.transparentAccountsRepository
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TransparentAccountsChooseViewModelTest {

    @Test
    fun `should map transactions to state`() = runTest {
        val transparentAccount1 = transparentAccount(
            accountNumber = AccountNumber("0001", "1234"),
            name = "first",
            totalAmount = Money("100.0", "CZK"),
        )
        val transparentAccount2 = transparentAccount(
            accountNumber = AccountNumber("0002", "1234"),
            name = "second",
            totalAmount = Money("-100.0", "CZK"),
        )
        val transparentAccount3 = transparentAccount(
            accountNumber = AccountNumber("0003", "1234"),
            name = "third",
            totalAmount = Money("10.0", "EUR"),
        )
        val accounts = listOf(
            transparentAccount1,
            transparentAccount2,
            transparentAccount3,
        )
        val expected = listOf(
            State.AccountItem(model = transparentAccount1, amount = "100.0 CZK"),
            State.AccountItem(model = transparentAccount2, amount = "-100.0 CZK"),
            State.AccountItem(model = transparentAccount3, amount = "10.0 EUR"),
        )
        val fetchAccounts = fetchAccountsUseCase(
            repository = transparentAccountsRepository(accounts = accounts),
        )

        val viewModel = viewModel(fetchAccounts = fetchAccounts)

        val job = launch {
            viewModel.states.collectLatest { state ->
                state.accounts shouldBe expected
                cancel()
            }
        }

        job.join()
    }

    @Test
    fun `should store selected transparent account on account item`() = runTest {
        val repository: SelectedTransparentAccountRepository = mockk(relaxed = true)
        val viewModel = viewModel(repository = repository)
        val transparentAccount = transparentAccount()
        val selectedAccount = State.AccountItem(transparentAccount)

        viewModel.onAccountItem(selectedAccount)

        verify { repository.store(transparentAccount) }
    }

    private fun viewModel(
        fetchAccounts: TransparentAccountsUseCase.FetchAccounts = fetchAccountsUseCase(),
        repository: SelectedTransparentAccountRepository = mockk(relaxed = true),
    ) = TransparentAccountsChooseViewModel(
        fetchAccounts = fetchAccounts,
        selectedTransparentAccountRepository = repository,
    )
}