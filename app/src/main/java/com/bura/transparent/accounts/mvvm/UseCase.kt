package com.bura.transparent.accounts.mvvm

interface UnitUseCase<out Output> {
    operator fun invoke(): Output
}

interface SuspendUnitUseCase<out Output> {
    suspend operator fun invoke(): Output
}

