package com.bura.transparent.accounts.scene

import androidx.lifecycle.viewModelScope
import com.bura.transparent.accounts.domain.SelectedTransparentAccountRepository
import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.model.TransparentAccount
import com.bura.transparent.accounts.mvvm.StatefulViewModel
import com.bura.transparent.accounts.mvvm.ViewModelState
import com.bura.transparent.accounts.scene.TransparentAccountsChooseViewModel.State
import kotlinx.coroutines.launch

class TransparentAccountsChooseViewModel(
    private val fetchAccounts: TransparentAccountsUseCase.FetchAccounts,
    private val selectedTransparentAccountRepository: SelectedTransparentAccountRepository,
): StatefulViewModel<State>(State()) {

    init {
        fetch()
    }

    fun onAccountItem(account: State.AccountItem) {
        selectedTransparentAccountRepository.store(account.model)
    }

    fun onErrorRetry() {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        state = state.copy(loading = true)

        fetchAccounts().fold(
            onSuccess = { accounts -> state = accounts.toState() },
            onFailure = { state = state.copy(loading = false, error = true) }
        )
    }

    private fun List<TransparentAccount>.toState() = State(
        accounts = map {
            State.AccountItem(
                model = it,
                amount = "${it.totalAmount.amount} ${it.totalAmount.currency}"
            )
        },
        loading = false,
        error = false,
    )

    data class State(
        val accounts: List<AccountItem> = emptyList(),
        val loading: Boolean = false,
        val error: Boolean = false,
    ): ViewModelState {

        data class AccountItem(
            val model: TransparentAccount,
            val amount: String? = "",
        )
    }
}