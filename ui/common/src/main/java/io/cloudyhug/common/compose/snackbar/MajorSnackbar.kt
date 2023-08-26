package io.cloudyhug.common.compose.snackbar

import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import io.cloudyhug.common.model.snackbar.SnackbarType

@Composable
fun MajorSnackbar(data: SnackbarData) {
    MajorSnackbarContent(
        message = data.visuals.message,
        onClick = { data.dismiss() },
        type = data.visuals.actionLabel.snackBarLabelToType()
    )
}

private fun String?.snackBarLabelToType(): SnackbarType {
    return SnackbarType.getTypeByName(this)
}
