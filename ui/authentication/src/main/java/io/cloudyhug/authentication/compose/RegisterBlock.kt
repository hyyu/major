package io.cloudyhug.authentication.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.cloudyhug.ui.authentication.R.string

@Composable
fun RegisterBlock(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .clickable { onClick.invoke() },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = string.label_suggest_to_register),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
