package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.QRFragmentScreenState
import org.koin.core.KoinComponent

class QRFragmentViewModel : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<QRFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<QRFragmentScreenState>>
        get() = _screenState

    fun loadQueueToJoin(get: String) {

        _screenState.value = ScreenState.Loading
        TODO("Not yet implemented")
    }
}