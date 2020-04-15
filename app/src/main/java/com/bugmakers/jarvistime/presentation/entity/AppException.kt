package com.bugmakers.jarvistime.presentation.entity

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.utils.StringResource
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage

internal val AppException.appUIMessage: AppUIMessage
    get() = AppUIMessage(TypeUIMessage.ERROR, errorMessage)

internal sealed class AppException(
    val errorMessage: StringResource,
    val throwable: Throwable? = null
) {

    class NetworkException(
        errorMessage: StringResource = StringResource(messageResId = R.string.error_internet_connection),
        throwable: Throwable? = null
    ) : AppException(errorMessage, throwable)

    class SomethingBadHappenException(
        errorMessage: StringResource = StringResource(messageResId = R.string.error_something_bad_happen),
        throwable: Throwable? = null
    ) : AppException(errorMessage, throwable)

    open class HttpException(
        val errorCode: Int,
        errorMessage: StringResource,
        throwable: Throwable? = null
    ) : AppException(errorMessage, throwable) {

        class ClientException(
            errorCode: Int,
            errorMessage: StringResource = StringResource(messageResId = R.string.error_client_side_error),
            throwable: Throwable? = null
        ) : HttpException(errorCode, errorMessage, throwable)

        class ServerException(
            errorCode: Int,
            errorMessage: StringResource = StringResource(messageResId = R.string.error_server_side_error),
            throwable: Throwable? = null
        ) : HttpException(errorCode, errorMessage, throwable)
    }
}