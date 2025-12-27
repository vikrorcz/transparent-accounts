package com.bura.transparent.accounts.scene

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.bura.transparent.accounts.R
import com.bura.transparent.accounts.navigation.Route
import com.bura.transparent.accounts.scene.TransparentAccountsChooseViewModel.State
import com.bura.transparent.accounts.scene.component.Card
import com.bura.transparent.accounts.scene.component.Error
import com.bura.transparent.accounts.scene.component.Spinner
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransparentAccountsChooseScreen(navController: NavController) {
    val viewModel: TransparentAccountsChooseViewModel = koinViewModel()
    val state = viewModel.states.collectAsState().value

    val onAccountItem = { account: State.AccountItem ->
        viewModel.onAccountItem(account)
        navController.navigate(Route.TRANSPARENCY_ACCOUNTS_OVERVIEW)
    }

    Screen(
        state = state,
        onErrorRetry = viewModel::onErrorRetry,
        onAccountItem = onAccountItem,
    )
}

@Composable
private fun Screen(
    state: State,
    onErrorRetry: () -> Unit,
    onAccountItem: (State.AccountItem) -> Unit,
) {
    Scaffold(
        topBar = { Header() }
    ) { paddingValues ->
        when {
            state.loading -> Spinner()
            state.error -> Error(onClick = onErrorRetry)
            else -> Content(
                state = state,
                paddingValues = paddingValues,
                onAccountItem = onAccountItem,
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
    onAccountItem: (State.AccountItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
    ) {
        items(state.accounts) { account ->
            Card(
                label = stringResource(R.string.transparent_accounts_choose_screen_label),
                value = account.model.name,
                secondaryValue = account.amount,
                onClick = { onAccountItem(account) },
            )
        }
    }
}

@Preview(locale = "en")
@Composable
private fun TransparentAccountsChooseScreenPreview() {
    Screen(
        state = State(),
        onErrorRetry = {},
        onAccountItem = {},
    )
}