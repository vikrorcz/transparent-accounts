package com.bura.transparent.accounts.scene

import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.fetchOverviewUseCase
import com.bura.transparent.accounts.loadSelectedUseCase
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.scene.TransparentAccountsOverviewViewModel.State
import com.bura.transparent.accounts.transparentAccountsRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TransparentAccountsOverviewViewModelTest {

    @Test
    fun `should map transactions to state`() = runTest {
        val transactions = listOf(
            Transaction(null, null, Money("250.0", "CZK")),
            Transaction("name", null, Money("-250.0", "CZK")),
            Transaction(null, "note", Money("10.0", "EUR")),
        )
        val expected = listOf(
            State.TransactionItem(name = null, note = null, amount = "250.0 CZK"),
            State.TransactionItem(name = "name", note = null, amount = "-250.0 CZK"),
            State.TransactionItem(name = null, note = "note", amount = "10.0 EUR"),
        )
        val fetchOverview = fetchOverviewUseCase(
            repository = transparentAccountsRepository(transactions = transactions),
        )

        val viewModel = viewModel(fetchOverview = fetchOverview)

        val job = launch {
            viewModel.states.collectLatest { state ->
                state.transactions shouldBe expected
                cancel()
            }
        }

        job.join()
    }

    private fun viewModel(
        fetchOverview: TransparentAccountsUseCase.FetchOverview = fetchOverviewUseCase(),
        loadSelected: TransparentAccountsUseCase.LoadSelected = loadSelectedUseCase(),
    ) = TransparentAccountsOverviewViewModel(
        fetchOverview = fetchOverview,
        loadSelected = loadSelected,
    )
}