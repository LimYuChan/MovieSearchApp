package com.devsurfer.domain.state

import com.devsurfer.domain.utils.Constants

//각 Exception 별  에러를 처리할 때 사용하기 위해 작성 된 class 입니다.
// Paging 라이브러리 자체에서 오류 핸들링을 제공하여 현재는 UnHandle만 사용합니다.
sealed class Failure(val message: String){
    class IncorrectQuery(message: String): Failure(message)
    class InvalidSearchApi(message: String): Failure(message)
    class SearchSystemError(message: String): Failure(message)
    class NetworkNotConnected(message: String): Failure(message)
    class UnHandle(message: String): Failure(message)
}
