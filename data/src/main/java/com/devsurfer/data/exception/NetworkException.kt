package com.devsurfer.data.exception

import com.devsurfer.domain.utils.Constants

sealed class NetworkException(message: String): Throwable(message = message){
    class BadRequestException: NetworkException(message = Constants.ERROR_MESSAGE_NOT_FOUND)
    class NetworkNotConnectedException: NetworkException(message = Constants.ERROR_MESSAGE_INTERNET_CONNECTED)
    class NotAvailableAccessKeyException: NetworkException(message = Constants.ERROR_MESSAGE_NOT_AVAILABLE_ACCESS_KEY)
    class UnHandleException: NetworkException(message = Constants.ERROR_MESSAGE_UNHANDLED)
}
