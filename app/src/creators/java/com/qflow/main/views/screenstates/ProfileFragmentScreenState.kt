package com.qflow.main.views.screenstates

sealed class ProfileFragmentScreenState {
    class AccessProfile(val id: String): ProfileFragmentScreenState()
}