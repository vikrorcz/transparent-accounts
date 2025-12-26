package com.bura.transparent.accounts.di

import com.bura.transparent.accounts.data.TransparentAccountsRepositoryImpl
import com.bura.transparent.accounts.domain.TransparentAccountsRepository
import com.bura.transparent.accounts.domain.TransparentAccountsUseCase
import com.bura.transparent.accounts.scene.TransparentAccountsChooseViewModel
import com.bura.transparent.network.di.NetworkGraph
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal object AppGraph {

    val module = module {
        includes(NetworkGraph.module)

        single<TransparentAccountsRepository> { new(::TransparentAccountsRepositoryImpl) }

        factoryOf(TransparentAccountsUseCase::FetchAccounts)

        viewModelOf(::TransparentAccountsChooseViewModel)
    }
}