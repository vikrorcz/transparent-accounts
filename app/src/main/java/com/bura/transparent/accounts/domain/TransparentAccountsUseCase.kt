package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.model.TransparentAccount
import com.bura.transparent.accounts.mvvm.SuspendUnitUseCase

interface TransparentAccountsUseCase {

    class FetchAccounts(
        private val repository: TransparentAccountsRepository
    ): SuspendUnitUseCase<Result<List<TransparentAccount>>> {

        override suspend fun invoke(): Result<List<TransparentAccount>> {
            return repository.fetchTransparentAccounts()
        }
    }

    class FetchOverview(
        private val repository: TransparentAccountsRepository,
        private val selectedTransparentAccountRepository: SelectedTransparentAccountRepository,
    ): SuspendUnitUseCase<Result<List<Transaction>>> {

        override suspend fun invoke(): Result<List<Transaction>> {
            val selectedAccount = selectedTransparentAccountRepository.load()
            return repository.fetchTransparentAccountTransactions(accountNumber = selectedAccount)
        }
    }
}