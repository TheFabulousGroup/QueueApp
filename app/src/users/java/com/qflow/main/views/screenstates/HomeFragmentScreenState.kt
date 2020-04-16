package com.qflow.main.views.screenstates

sealed class HomeFragmentScreenState {
    class AccessProfile(val id: String): HomeFragmentScreenState()
}