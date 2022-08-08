package com.devsurfer.domain.exception

import com.devsurfer.domain.utils.Constants

//Network 작업을 하면서 발생할 수 있는 Exception 입니다
//따로 sealed class로 만들어 핸들링 할 수 있도록 구성하였습니다.
//todo 실제 프로젝트에서 기획 의도에 맞게 오류를 핸들링 하기 위해 구성
sealed class NetworkException(message: String): Throwable(message = message){
    class IncorrectQuery: NetworkException(message = Constants.ERROR_MESSAGE_INCORRECT_QUERY)
    class InvalidSearchApi: NetworkException(message = Constants.ERROR_MESSAGE_INVALID_SEARCH_API)
    class SearchSystemError: NetworkException(message = Constants.ERROR_MESSAGE_SEARCH_SYSTEM_ERROR)
    class NetworkNotConnected: NetworkException(message = Constants.ERROR_MESSAGE_NETWORK_CONNECTED)
}
