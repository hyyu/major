package io.cloudyhug.major

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.cloudyhug.authentication.state.Page
import io.cloudyhug.authentication.compose.Authentication
import io.cloudyhug.common.frame.model.AppBarData
import io.cloudyhug.home.compose.Home
import io.cloudyhug.major.ui.theme.MajorTheme
import io.cloudyhug.navigation.navigator.AuthNavigator
import io.cloudyhug.navigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MajorTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = Screen.valueOf(
                    backStackEntry?.destination?.route ?: Screen.Login.name
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        navController = navController,
                        currentScreen = currentScreen
                    )
                }
            }
        }
    }

    @Composable
    private fun Navigation(
        navController: NavHostController,
        currentScreen: Screen,
    ) {
        val authNavigator = AuthNavigator(
            navigateUp = { navController.navigateUp() },
            navigateToRegister = { navController.navigate(Screen.Register.name) },
            onUserAuthenticated = {
                navController.navigate(Screen.Home.name) {
                    popUpTo(Screen.Login.name) {
                        inclusive = true
                    }
                }
            }
        )

        val appBarData = AppBarData(
            canNavigateBack = navController.previousBackStackEntry != null,
            currentScreen = currentScreen
        )

        NavHost(
            navController = navController,
            route = Screen.Root.name,
            startDestination = Screen.Login.name
        ) {
            composable(route = Screen.Login.name) {
                Authentication(
                    viewModel = hiltViewModel(),
                    navigator = authNavigator,
                    appBarData = appBarData,
                    page = Page.Login
                )
            }
            composable(route = Screen.Register.name) {
                Authentication(
                    viewModel = hiltViewModel(),
                    navigator = authNavigator,
                    appBarData = appBarData,
                    page = Page.Register,
                )
            }
            composable(route = Screen.Home.name) {
                Home(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }

}
