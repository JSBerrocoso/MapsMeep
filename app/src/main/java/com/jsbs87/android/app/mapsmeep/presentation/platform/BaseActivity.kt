package com.jsbs87.android.app.mapsmeep.presentation.platform

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jsbs87.android.app.mapsmeep.R
import com.jsbs87.android.app.mapsmeep.domain.exception.Failure
import com.jsbs87.android.app.mapsmeep.presentation.extension.gone
import com.jsbs87.android.app.mapsmeep.presentation.extension.visible
import com.jsbs87.android.app.mapsmeep.presentation.util.LoadingHandler
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.layout_loading.*

abstract class BaseActivity : AppCompatActivity(), LoadingHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initUI()
    }

    abstract fun initUI()

    abstract fun layoutId(): Int

    private fun showAlertDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                R.string.ok
            ) { _, _ -> }
            .show()
    }

    override fun showLoading() {
        ll_loading.visible()
    }

    override fun hideLoading() {
        ll_loading.gone()
    }

    open fun handleLoading(showLoading: Boolean?) {
        if (showLoading == true) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    open fun showError(failure: Failure?) {
        hideLoading()
        when (failure) {
            is Failure.ServerError -> {
                showSnackBar(getString(R.string.server_error))
            }
            is Failure.NetworkConnection -> {
                showSnackBar(getString(R.string.network_error))
            }
            else -> {
                failure?.message?.let { showAlertDialog("Error", it) }
            }
        }
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(toolbar, text, Snackbar.LENGTH_LONG).show()
    }

    abstract fun refresh()

}
