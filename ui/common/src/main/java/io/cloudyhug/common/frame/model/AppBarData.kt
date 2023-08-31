package io.cloudyhug.common.frame.model

import io.cloudyhug.navigation.Screen

data class AppBarData(
    val canNavigateBack: Boolean,
    val currentScreen: Screen
)
