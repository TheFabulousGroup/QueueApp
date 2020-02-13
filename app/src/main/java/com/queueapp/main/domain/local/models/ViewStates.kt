package com.queueapp.main.domain.local.models

data class ViewStates (val code: Long, val screen: ViewStatesMessageTypes)

enum class ViewStatesMessageTypes (val value: String) {
    USER_ASSIGNED("user_assinged"),
    LOGIN_NOT_SUCCESSFUL("user_not_found"),
    SIGN_IN_FAILED("user_not_created_correctly")

}