package com.jsbs87.android.app.mapsmeep.domain.exception

import android.util.Log

sealed class Failure( var message: String? = null) {
    private val TAG: String = Failure::class.java.simpleName

    init {
        if (message != null) Log.e(TAG, message!!)
    }

    object NetworkConnection : Failure()
    object ServerError : Failure()
    object GeResourcesError : Failure()

    abstract class FeatureFailure(message: String? = null) : Failure(message) {

        companion object {
            fun getFromCode(code: Int): FeatureFailure {
                return when (code) {
                    400 -> ConnectionError()
                    else -> GenericFailure(code)
                }
            }
        }
    }

    class GenericFailure(val code: Int = -1, message: String? = null) : FeatureFailure(message)

    class ConnectionError : FeatureFailure()

}
