package com.qflow.main.views.screenstates

sealed class SignUpFragmentScreenState {
    class UserCreatedCorrectly(val id: String): SignUpFragmentScreenState()
}