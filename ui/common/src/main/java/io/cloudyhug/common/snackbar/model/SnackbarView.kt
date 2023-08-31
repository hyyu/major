package io.cloudyhug.common.snackbar.model

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.cloudyhug.common.snackbar.model.SnackbarType.*

data class SnackbarView(
    val backgroundColor: Color,
    val image: ImageVector,
    val iconModifier: Modifier,
    val tint: Color
)

fun SnackbarType.toModel(): SnackbarView {
    when (this) {
        VALIDATION -> {
            return SnackbarView(
                backgroundColor = Color(0xFF00892A),
                image = Icons.Filled.CheckCircle,
                iconModifier = Modifier.size(20.dp),
                tint = Color(0xFFFFFFFF),
            )
        }
        ERROR -> {
            return SnackbarView(
                backgroundColor = Color(0xFFC91432),
                image = Icons.Filled.Info,
                iconModifier = Modifier
                    .rotate(180.0f)
                    .size(20.dp),
                tint = Color(0xFFFFFFFF),
            )
        }

        NEUTRAL -> {
            return SnackbarView(
                backgroundColor = Color(0xFF727376),
                image = Icons.Filled.Info,
                iconModifier = Modifier
                    .size(20.dp),
                tint = Color(0xFFFFFFFF),
            )
        }
    }
}
