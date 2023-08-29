package io.cloudyhug.major.ui.compose.preview.scaffold

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.cloudyhug.common.frame.compose.MajorAppBar
import io.cloudyhug.major.ui.theme.MajorTheme
import io.cloudyhug.navigation.Screen

@Preview(name = "Light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMajorAppBar() {
    MajorTheme {
        MajorAppBar(
            currentScreen = Screen.Home,
            canNavigateBack = false,
            navigateUp = {}
        )
    }
}
