package com.bura.transparent.accounts.domain

import com.bura.transparent.accounts.model.TransparentAccount

interface SelectedTransparentAccountRepository {

    fun store(transparentAccount: TransparentAccount)

    fun load(): TransparentAccount
}