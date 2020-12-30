package com.jsbs87.android.app.mapsmeep.presentation.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsbs87.android.app.mapsmeep.domain.exception.Failure

abstract class BaseViewModel : ViewModel() {

    var failure = SingleLiveEvent<Failure>()
    var loading = SingleLiveEvent<Boolean>()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    protected fun showLoading() {
        loading.value = true
    }

    protected fun hideLoading() {
        loading.value = false
        viewModelScope
    }

    fun isLoading(): Boolean {
        return loading.value ?: false
    }
}
