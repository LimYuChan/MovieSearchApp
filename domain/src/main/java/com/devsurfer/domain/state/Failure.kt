package com.devsurfer.domain.state

sealed class Failure(val message: String){
    class BadRequest(message: String): Failure(message)
    class NetworkNotConnected(message: String): Failure(message)
    class NotAvailableAccessKey(message: String): Failure(message)
    class UnHandle(message: String): Failure(message)
}
