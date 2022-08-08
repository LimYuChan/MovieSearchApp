package com.devsurfer.domain.state

//데이터 처리의 결과를 나타내는 class 입니다.
//todo 프로젝트가 커지면 UI 단에서 Failure를 컨트롤하는 기능을 Fragment 또는 Activity extension 함수로 만들어서 관리할 계획입니다.
sealed class ResourceState<T>{
    data class Success<T>(val data: T): ResourceState<T>()
    data class Error<T>(val data:T? = null, val failure: Failure): ResourceState<T>()
}
