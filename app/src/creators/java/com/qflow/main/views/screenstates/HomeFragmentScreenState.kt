package com.qflow.main.views.screenstates

sealed class HomeFragmentScreenState {
    class AccessHome(val id: String): HomeFragmentScreenState()
}