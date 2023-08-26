package io.cloudyhug.common.compose.frame

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import io.cloudyhug.common.compose.snackbar.MajorSnackbar
import io.cloudyhug.common.model.snackbar.MajorSnackbarData
import io.cloudyhug.navigation.Screen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun Frame(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    snackbarState: StateFlow<MajorSnackbarData?>? = null,
    onShowSnackbar: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val showSnackbar: (MajorSnackbarData) -> Unit = {
        coroutineScope.launch {
            onShowSnackbar?.invoke()
            snackbarHostState.showSnackbar(
                message = it.message,
                actionLabel = it.type.name,
                duration = SnackbarDuration.Short
            )
        }
    }

    snackbarState?.collectAsState()?.value.also { data ->
        data?.let {
            showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MajorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                MajorSnackbar(
                    data = data
                )
            }
        },
        content = content
    )
}