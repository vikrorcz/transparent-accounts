package com.bura.transparent.accounts.scene

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bura.transparent.accounts.R
import com.bura.transparent.accounts.scene.TransparentAccountsChooseViewModel.State
import com.bura.transparent.accounts.scene.component.Card
import com.bura.transparent.accounts.scene.component.Error
import com.bura.transparent.accounts.scene.component.Spinner
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransparentAccountsChooseScreen() {
    val viewModel: TransparentAccountsChooseViewModel = koinViewModel()
    val state = viewModel.states.collectAsState().value

    Screen(
        state = state,
        onBack = {},
        onErrorRetry = viewModel::onErrorRetry,
    )
}

@Composable
private fun Screen(
    state: State,
    onBack: () -> Unit,
    onErrorRetry: () -> Unit,
) {
    BackHandler(onBack = onBack)
    Scaffold(
        topBar = { Header() }
    ) { paddingValues ->
        when {
            state.loading -> Spinner()
            state.error -> Error(onClick = onErrorRetry)
            else -> Content(
                state = state,
                paddingValues = paddingValues,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header() {
    TopAppBar(title = { Text(stringResource(R.string.transparent_accounts_choose_screen_title)) })
}

@Composable
private fun Content(
    state: State,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
    ) {
        itemsIndexed(state.accounts) { index, account ->
            account.name?.let {
                Card(
                    label = account.name,
                    value = account.amount,
                    onClick = {},
                )
            }
        }
    }
}

@Preview(locale = "en")
@Composable
private fun TransparentAccountsChooseScreenPreview() {
    Screen(
        state = State(),
        onBack = {},
        onErrorRetry = {},
    )
}