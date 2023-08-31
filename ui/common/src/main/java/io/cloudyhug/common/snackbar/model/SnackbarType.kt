package io.cloudyhug.common.snackbar.model

enum class SnackbarType {
    VALIDATION, ERROR, NEUTRAL;

    companion object {
        fun getTypeByName(name: String?): SnackbarType {
            name?.let {
                return try {
                    valueOf(name.uppercase())
                } catch (_: IllegalArgumentException) {
                    ERROR
                }
            }
            return ERROR
        }
    }

}
