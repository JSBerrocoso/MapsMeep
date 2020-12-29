package com.jsbs87.android.app.mapsmeep.domain.interactors

import com.jsbs87.android.app.mapsmeep.domain.exception.Failure
import com.jsbs87.android.omtest.app.domain.functional.Either
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        uiScope.launch {
            val deferred = async(Dispatchers.Default) { run(params) }
            onResult(deferred.await())
        }
    }

    object None
}