package com.example.mvvm_template.data.error


sealed class Failure {
    object NetworkConnection : Failure()
    object  UnAuthorize : Failure()
    sealed class ServerError : Failure() {
        object NotFound : ServerError()
        object ServiceUnavailable : ServerError()
        object AccessDenied : ServerError()
        object InternalServerError : ServerError()
    }

    object UnknownError : Failure(){
        var message=""
    }
}