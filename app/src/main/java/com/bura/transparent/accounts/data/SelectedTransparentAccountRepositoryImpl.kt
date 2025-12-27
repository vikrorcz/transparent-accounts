package com.bura.transparent.accounts.data

import com.bura.transparent.accounts.domain.SelectedTransparentAccountRepository
import com.bura.transparent.accounts.model.TransparentAccount
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedTransparentAccountRepositoryImpl: SelectedTransparentAccountRepository {

    private val selectedTransparentAccount = MutableStateFlow<TransparentAccount?>(null)

    override fun store(transparentAccount: TransparentAccount) {
        selectedTransparentAccount.value = transparentAccount
    }

    override fun load(): TransparentAccount = selectedTransparentAccount.value ?: error("Selected transparent account missing")
}