package com.bura.transparent.accounts.scene

import androidx.lifecycle.viewModelScope
import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.mvvm.StatefulViewModel
import com.bura.transparent.accounts.mvvm.ViewModelState
import com.bura.transparent.accounts.scene.TransparentAccountsOverviewViewModel.State
import com.bura.transparent.accounts.scene.TransparentAccountsOverviewViewModel.State.TransactionItem
import kotlinx.coroutines.launch

class TransparentAccountsOverviewViewModel(
    private val fetchOverview: TransparentAccountsUseCase.FetchOverview,
    private val loadSelected: TransparentAccountsUseCase.LoadSelected,
): StatefulViewModel<State>(State()) {

    init {
        fetch()
    }

    fun onErrorRetry() {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        state = state.copy(loading = true)

        fetchOverview().fold(
            onSuccess = { transactions -> state = transactions.toState() },
            onFailure = { throwable -> state = state.copy(loading = false, error = true) }
        )
    }

    private fun List<Transaction>.toState() = State(
        accountName = loadSelected().name,
        transactions = map {
            TransactionItem(
                name = it.name,
                note = it.note,
                amount = "${it.money.amount} ${it.money.currency}"
            )
        },
        loading = false,
        error = false,
    )

    data class State(
        val accountName: String? = "",
        val transactions: List<TransactionItem> = emptyList(),
        val loading: Boolean = false,
        val error: Boolean = false,
    ): ViewModelState {

        data class TransactionItem(
            val name: String? = "",
            val note: String? = "",
            val amount: String? = "",
        )
    }
}