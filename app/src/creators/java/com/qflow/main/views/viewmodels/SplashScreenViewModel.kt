package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.views.screenstates.SplashScreenScreenState
import org.koin.core.KoinComponent

class SplashScreenViewModel(private val sharedPrefsRepository: SharedPrefsRepository) :
    BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<SplashScreenScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<SplashScreenScreenState>>
        get() = _screenState

    fun checkIfUserIsLogged() {
        if (sharedPrefsRepository.getUserToken() != null)
            _screenState.value = ScreenState.Render(SplashScreenScreenState.UserIsLogged)
        else
            _screenState.value = ScreenState.Render(SplashScreenScreenState.UserIsNotLogged)
    }


}
