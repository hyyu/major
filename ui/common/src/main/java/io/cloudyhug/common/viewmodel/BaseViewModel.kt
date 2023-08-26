package io.cloudyhug.common.viewmodel

import androidx.lifecycle.ViewModel
import io.cloudyhug.common.model.snackbar.MajorSnackbarData
import io.cloudyhug.common.model.snackbar.SnackbarType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    private val _snackbarState = MutableStateFlow<MajorSnackbarData?>(null)
    val snackbarState: StateFlow<MajorSnackbarData?> = _snackbarState

    protected fun showSnackBar(message: String, type: SnackbarType = SnackbarType.ERROR) {
        _snackbarState.value = MajorSnackbarData(message, type)
    }

    fun snackbarDisplayed() {
        _snackbarState.value = null
    }

}
