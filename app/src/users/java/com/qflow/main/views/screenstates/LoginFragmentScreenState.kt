package com.qflow.main.views.screenstates

sealed class LoginFragmentScreenState {
    class UserCreatedCorrectly(val id: Long): LoginFragmentScreenState()
}