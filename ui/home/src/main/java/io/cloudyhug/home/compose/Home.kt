package io.cloudyhug.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import io.cloudyhug.common.compose.button.ButtonWithLoader
import io.cloudyhug.home.HomeViewModel
import io.cloudyhug.home.event.HomeEvent
import io.cloudyhug.ui.home.R.string

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ButtonWithLoader(
            modifier = Modifier
                .fillMaxWidth(0.45f),
            isLoading = state.isLoading,
            title = stringResource(string.label_get_user),
            onClickAction = { viewModel.handleEvent(HomeEvent.GetUser) }
        )
    }

}
