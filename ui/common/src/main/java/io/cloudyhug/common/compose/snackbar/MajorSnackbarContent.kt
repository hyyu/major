package io.cloudyhug.common.compose.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.cloudyhug.common.model.snackbar.SnackbarType
import io.cloudyhug.common.model.snackbar.toModel
import io.cloudyhug.ui.common.R

@Composable
fun MajorSnackbarContent(
    message: String?,
    onClick: (() -> Unit)? = null,
    type: SnackbarType = SnackbarType.ERROR,
) {
    val snackBarModel = type.toModel()

    val containerColor = when (type) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.errorContainer
        SnackbarType.NEUTRAL -> MaterialTheme.colorScheme.surface
        SnackbarType.VALIDATION -> MaterialTheme.colorScheme.tertiaryContainer
    }

    val onContainerColor = when (type) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
        SnackbarType.NEUTRAL -> MaterialTheme.colorScheme.onSurface
        SnackbarType.VALIDATION -> MaterialTheme.colorScheme.onTertiaryContainer
    }

    val iconTint = if (isSystemInDarkTheme())
        onContainerColor
    else
        when (type) {
            SnackbarType.ERROR -> MaterialTheme.colorScheme.error
            SnackbarType.NEUTRAL -> MaterialTheme.colorScheme.onSurface
            SnackbarType.VALIDATION -> MaterialTheme.colorScheme.tertiary
        }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            ),
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick?.invoke() }
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    imageVector = snackBarModel.image,
                    contentDescription = stringResource(id = R.string.icon_snackbar_content_description),
                    tint = iconTint,
                    modifier = snackBarModel.iconModifier,
                )
                Text(
                    text = message ?: "",
                    color = onContainerColor,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
