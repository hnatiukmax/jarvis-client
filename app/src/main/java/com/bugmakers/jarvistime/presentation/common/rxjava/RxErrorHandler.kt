package com.bugmakers.jarvistime.presentation.common.rxjava

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.entity.AppException
import com.bugmakers.jarvistime.presentation.utils.StringResource
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

internal inline fun Completable.doOnError(
    crossinline doOnError: (error: AppException) -> Unit
) : Completable {
    return doOnError {
        val error = getError(it)

        doOnError.invoke(error)
        onErrorComplete()
    }
}

internal inline fun <T> Observable<T>.doOnError(
    crossinline doOnError: (AppException) -> Unit
) : Observable<T> {
    return doOnError {
        val error = getError(it)

        doOnError.invoke(error)
    }
}

internal inline fun <T> Single<T>.doOnError(
    crossinline doOnError: (AppException) -> Unit
) : Single<T> {
    return doOnError {
        val error = getError(it)

        doOnError.invoke(error)
    }
}

internal inline fun <T> Flowable<T>.doOnError(
    crossinline doOnError: (AppException) -> Unit
) : Flowable<T> {
    return doOnError {
        val error = getError(it)

        doOnError.invoke(error)
    }
}

private fun getError(error: Throwable) = when (error) {
    is ConnectException,
    is UnknownHostException -> AppException.NetworkException(throwable = error)
    is HttpException -> {
        when (error.code()) {
            in 400..499 -> {
                val defaultErrorMessage = R.string.error_client_side_error.asStringResources
                AppException.HttpException.ServerException(
                    errorCode = error.code(),
                    errorMessage = error.getErrorMessage() ?: defaultErrorMessage
                )
            }
            in 500..599 -> {
                val defaultErrorMessage = R.string.error_server_side_error.asStringResources
                AppException.HttpException.ServerException(
                    errorCode = error.code(),
                    errorMessage = error.getErrorMessage() ?: defaultErrorMessage
                )
            }
            else -> AppException.HttpException(
                errorCode = error.code(),
                errorMessage = error.message().asStringResources
            )
        }
    }
    else -> AppException.SomethingBadHappenException(throwable = error)
}


private fun HttpException.getErrorMessage() : StringResource? {
    return response()?.errorBody()?.string()?.let {
        try {
            JSONObject(it).getString("error").asStringResources
        } catch (e: Throwable) {
            null
        }
    }
}
