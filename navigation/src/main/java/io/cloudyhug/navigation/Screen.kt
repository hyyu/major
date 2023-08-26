package io.cloudyhug.navigation

import androidx.annotation.StringRes
import io.cloudyhug.navigation.R.string

enum class Screen(@StringRes val title: Int) {
    Root(title = string.title_root_navigator),
    Login(title = string.title_auth_page),
    Register(title = string.title_register_page),
    Home(title = string.title_home_page)
}
