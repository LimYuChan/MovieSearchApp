package com.devsurfer.domain.utils

object Constants {
    const val CODE_INCORRECT_QUERY = 400
    const val CODE_INVALID_SEARCH_API = 404
    const val CODE_SEARCH_SYSTEM_ERROR = 500

    const val ERROR_MESSAGE_INCORRECT_QUERY = "검색 API 요청에 오류가 있습니다. 요청 URL, 필수 요청 변수가 정확한지 확인 바랍니다."
    const val ERROR_MESSAGE_INVALID_SEARCH_API = "검색 API 대상에 오타가 없는지 확인해 보세요."
    const val ERROR_MESSAGE_SEARCH_SYSTEM_ERROR = "서버 내부 에러가 발생하였습니다. 포럼에 올려주시면 신속히 조치하겠습니다."
    const val ERROR_MESSAGE_INTERNET_CONNECTED = "인터넷 연결을 확인해주세요."
    const val ERROR_MESSAGE_UNHANDLED ="알 수 없는 에러가 발생했습니다."
    const val ERROR_MESSAGE_SQL_INSERT_QUERY = "데이터를 입력중 오류가 발생했습니다."
    const val ERROR_MESSAGE_SQL_GET_QUERY = "데이터를 가져오던 중 오류가 발생했습니다."
}