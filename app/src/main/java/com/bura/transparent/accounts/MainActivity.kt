package com.bura.transparent.accounts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bura.transparent.accounts.navigation.Route
import com.bura.transparent.accounts.scene.TransparentAccountsChooseScreen
import com.bura.transparent.accounts.scene.TransparentAccountsOverviewScreen
import com.bura.transparent.accounts.ui.theme.TransparentAccountsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransparentAccountsTheme {
                ApplicationNavigationHost()
            }
        }
    }
}

@Composable
private fun ApplicationNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.TRANSPARENCY_ACCOUNTS_CHOOSE,
        modifier = Modifier.systemBarsPadding(),
    ) {
        composable(Route.TRANSPARENCY_ACCOUNTS_CHOOSE) { TransparentAccountsChooseScreen(navController) }
        composable(Route.TRANSPARENCY_ACCOUNTS_OVERVIEW) { TransparentAccountsOverviewScreen(navController) }
    }
}