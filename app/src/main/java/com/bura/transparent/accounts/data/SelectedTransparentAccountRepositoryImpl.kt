package com.bura.transparent.accounts.data

import com.bura.transparent.accounts.domain.SelectedTransparentAccountRepository
import com.bura.transparent.accounts.model.AccountNumber
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedTransparentAccountRepositoryImpl: SelectedTransparentAccountRepository {

    private val selectedTransparentAccount = MutableStateFlow<AccountNumber?>(null)

    override fun store(accountNumber: AccountNumber) {
        selectedTransparentAccount.value = accountNumber
    }

    override fun load(): AccountNumber = selectedTransparentAccount.value ?: error("Selected transparent account missing")

}