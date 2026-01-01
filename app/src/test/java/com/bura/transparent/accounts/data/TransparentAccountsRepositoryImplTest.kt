package com.bura.transparent.accounts.data

import com.bura.transparent.accounts.model.AccountNumber
import com.bura.transparent.accounts.model.Money
import com.bura.transparent.accounts.model.Transaction
import com.bura.transparent.accounts.model.TransparentAccount
import com.bura.transparent.network.data.TransparentAccountsApiService
import com.bura.transparent.network.model.AmountDto
import com.bura.transparent.network.model.PartyDto
import com.bura.transparent.network.model.TransparentAccountDto
import com.bura.transparent.network.model.TransparentAccountTransactionDto
import com.bura.transparent.network.model.TransparentAccountTransactionsPageDto
import com.bura.transparent.network.model.TransparentAccountsPageDto
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TransparentAccountsRepositoryImplTest {

    @Test
    fun `should map transparent accounts to domain`() = runTest {
        val transparentAccounts = transparentAccountsDto(
            accounts = listOf(
                transparentAccountDto(
                    accountNumber = "1",
                    bankCode = "0001",
                    name = "First",
                    balance = 100.0,
                    currency = "CZK",
                ),
                transparentAccountDto(
                    accountNumber = "2",
                    bankCode = "0002",
                    name = "Second",
                    balance = -100.0,
                    currency = "CZK",
                ),
                transparentAccountDto(
                    accountNumber = "3",
                    bankCode = "0003",
                    name = "Third",
                    balance = 10.0,
                    currency = "EUR",
                ),
            )
        )
        val expected = listOf(
            TransparentAccount(
                AccountNumber("1", "0001"),
                name = "First",
                totalAmount = Money("100.0", "CZK"),
            ),
            TransparentAccount(
                AccountNumber("2", "0002"),
                name = "Second",
                totalAmount = Money("-100.0", "CZK"),
            ),
            TransparentAccount(
                AccountNumber("3", "0003"),
                name = "Third",
                totalAmount = Money("10.0", "EUR"),
            ),
        )
        val api = api(transparentAccounts = transparentAccounts)
        val repository = repository(api = api)

        val result = repository.fetchTransparentAccounts()

        result.shouldBeSuccess(expected)
    }

    @Test
    fun `should map transparent account transactions to domain with selected account number`() = runTest {
        val transactions = transparentAccountTransactionsDto(
            transactions = listOf(
                transparentAccountTransactionDto(AmountDto(250.0, 1, "CZK")),
                transparentAccountTransactionDto(AmountDto(-250.0, 1, "CZK")),
                transparentAccountTransactionDto(AmountDto(10.0, 1, "EUR")),
            )
        )
        val expected = listOf(
            Transaction(null, null, Money("250.0", "CZK")),
            Transaction(null, null, Money("-250.0", "CZK")),
            Transaction(null, null, Money("10.0", "EUR")),
        )
        val selectedTransparentAccount = AccountNumber("123456", "1234")
        val api = api(
            transactions = transactions,
            selectedTransparentAccount = selectedTransparentAccount.accountNumber,
        )
        val repository = repository(api = api)

        val result = repository.fetchTransparentAccountTransactions(selectedTransparentAccount)

        result.shouldBeSuccess(expected)
    }

    private fun transparentAccountsDto(
        accounts: List<TransparentAccountDto> = emptyList(),
    ) = TransparentAccountsPageDto(
        pageNumber = 0,
        pageCount = 1,
        pageSize = 20,
        recordCount = 50,
        nextPage = null,
        accounts = accounts,
    )

    private fun transparentAccountDto(
        accountNumber: String = "accountNumber",
        bankCode: String = "bankCode",
        transparencyFrom: String = "transparencyFrom",
        transparencyTo: String = "transparencyTo",
        publicationTo: String = "publicationTo",
        actualizationDate: String = "actualizationDate",
        balance: Double = 0.0,
        currency: String? = null,
        name: String = "name",
        description: String? = null,
        note: String? = null,
        iban: String = "iban",
        statements: List<String> = emptyList()
    ) = TransparentAccountDto(
        accountNumber = accountNumber,
        bankCode = bankCode,
        transparencyFrom = transparencyFrom,
        transparencyTo = transparencyTo,
        publicationTo = publicationTo,
        actualizationDate = actualizationDate,
        balance = balance,
        currency = currency,
        name = name,
        description = description,
        note = note,
        iban = iban,
        statements = statements,
    )

    private fun transparentAccountTransactionsDto(
        transactions: List<TransparentAccountTransactionDto> = emptyList(),
    ) = TransparentAccountTransactionsPageDto(
        pageNumber = 0,
        pageCount = 1,
        pageSize = 20,
        recordCount = 50,
        nextPage = null,
        transactions = transactions,
    )

    private fun transparentAccountTransactionDto(
        amount: AmountDto = AmountDto(0.0,1,"CZK"),
        type: String = "type",
        dueDate: String = "dueDate",
        processingDate: String = "processingDate",
        typeDescription: String? = null,
        sender: PartyDto? = null,
        receiver: PartyDto? = null,
    ) = TransparentAccountTransactionDto(
        amount = amount,
        type = type,
        dueDate = dueDate,
        processingDate = processingDate,
        typeDescription = typeDescription,
        sender = sender,
        receiver = receiver,
    )

    private fun api(
        transparentAccounts: TransparentAccountsPageDto = transparentAccountsDto(),
        transactions: TransparentAccountTransactionsPageDto = transparentAccountTransactionsDto(),
        selectedTransparentAccount: String = "123",
    ): TransparentAccountsApiService  = mockk {
        coEvery { getTransparentAccounts() } returns transparentAccounts
        coEvery { getTransparentAccountTransactions(selectedTransparentAccount) } returns transactions
    }

    private fun repository(api:  TransparentAccountsApiService = api()) = TransparentAccountsRepositoryImpl(api)
}