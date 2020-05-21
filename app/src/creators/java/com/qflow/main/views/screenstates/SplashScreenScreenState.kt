package com.qflow.main.views.screenstates

sealed class SplashScreenScreenState {

    object UserIsLogged : SplashScreenScreenState()
    object UserIsNotLogged : SplashScreenScreenState()

}
