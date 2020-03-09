package com.qflow.main.views.screenstates

/**
 * Screenstates are used to implement the logic in views when something occurs in the viewModel
 * This one takes care of what happens in the login fragment
 **/
sealed class LoginFragmentScreenState {
    class LoginSuccessful(val id: String): LoginFragmentScreenState()
}