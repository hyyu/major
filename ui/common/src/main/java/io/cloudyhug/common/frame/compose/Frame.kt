package io.cloudyhug.common.frame.compose

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
import io.cloudyhug.common.frame.model.AppBarData
import io.cloudyhug.common.snackbar.compose.MajorSnackbar
import io.cloudyhug.common.snackbar.model.MajorSnackbarData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun Frame(
    modifier: Modifier = Modifier,
    appBarData: AppBarData,
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
                currentScreen = appBarData.currentScreen,
                canNavigateBack = appBarData.canNavigateBack,
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