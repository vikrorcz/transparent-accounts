package com.bura.transparent.accounts.domain

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
}