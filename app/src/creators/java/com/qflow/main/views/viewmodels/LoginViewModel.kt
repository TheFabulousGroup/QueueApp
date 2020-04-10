package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.screenstates.LoginFragmentScreenState

/**
 * Viewmodel of the LoginFragment, it connects with the usecases
 *
 *
 * */
class LoginViewModel(
    private val userLogin: LoginCase,
    private val createQueue: CreateQueue
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<LoginFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<LoginFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun login(
        selectedPass: String,
        selectedMail: String
    ) {
        //Execute add user to database
        userLogin.execute(
            { it.either(::handleFailure, ::handleLoginSuccessful) },
            LoginCase.Params(selectedPass, selectedMail),
            this.coroutineScope
        )
    }

    private fun handleLoginSuccessful(id: String) {
        this._screenState.value =
            ScreenState.Render(LoginFragmentScreenState.LoginSuccessful(id))
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun testFeature() {
        createQueue.execute(
            { it.either(::handleFailure, ::handleLoginSuccessful) },
            CreateQueue.Params("Prueba1", "Soy una prueba", "dec", 1),
            this.coroutineScope
        )
    }
}
