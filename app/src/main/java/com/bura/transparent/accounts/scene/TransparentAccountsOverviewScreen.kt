package com.bura.transparent.accounts.scene

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.bura.transparent.accounts.R
import com.bura.transparent.accounts.scene.TransparentAccountsOverviewViewModel.State
import com.bura.transparent.accounts.scene.component.Card
import com.bura.transparent.accounts.scene.component.Error
import com.bura.transparent.accounts.scene.component.Spinner
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransparentAccountsOverviewScreen() {
    val viewModel: TransparentAccountsOverviewViewModel = koinViewModel()
    val state = viewModel.states.collectAsState().value

    Screen(
        state = state,
        onErrorRetry = viewModel::onErrorRetry,
    )
}

@Composable
private fun Screen(
    state: State,
    onErrorRetry: () -> Unit,
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
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header() {
    TopAppBar(title = { Text(stringResource(R.string.transparent_accounts_overview_screen_title)) })
}

@Composable
private fun Content(
    state: State,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
    ) {
        item {
            state.accountName?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

        items(state.transactions) { transaction ->
            transaction.note?.let {
                Card(
                    label = it,
                    value = transaction.amount,
                    secondaryValue = transaction.name,
                    onClick = { },
                )
            }
        }
    }
}

@Preview(locale = "en")
@Composable
private fun TransparentAccountsOverviewScreenPreview() {
    Screen(
        state = State(),
        onErrorRetry = {},
    )
}