package io.cloudyhug.major.ui.compose.preview.scaffold

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.cloudyhug.common.compose.snackbar.MajorSnackbarContent
import io.cloudyhug.common.model.snackbar.SnackbarType
import io.cloudyhug.major.ui.theme.MajorTheme

@Preview(name = "Light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMajorSnackbar() {
    MajorTheme {
        Column {
            MajorSnackbarContent(
                message = "This is an error snackbar",
                type = SnackbarType.ERROR
            )
            MajorSnackbarContent(
                message = "This is a neutral snackbar",
                type = SnackbarType.NEUTRAL
            )
            MajorSnackbarContent(
                message = "This is a validation snackbar",
                type = SnackbarType.VALIDATION
            )
        }
    }
}
