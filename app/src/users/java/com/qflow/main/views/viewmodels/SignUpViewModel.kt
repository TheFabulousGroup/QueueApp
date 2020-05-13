package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.user.CreateUser
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class SignUpViewModel (

    private val createUser: CreateUser
    ) : BaseViewModel(), KoinComponent {

        private val _currentUser: MutableLiveData<Long> = MutableLiveData()
        val currentUser: LiveData<Long>
        get() = _currentUser

        private val _screenState: MutableLiveData<ScreenState<SignUpFragmentScreenState>> = MutableLiveData()
        val screenState: LiveData<ScreenState<SignUpFragmentScreenState>>
        get() = _screenState

        private var job = Job()
        private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

        fun saveUserInDatabase(
            selectedUsername: String,
            selectedEmail: String,
            selectedPass: String,
            selectedRepeatPass: String,
            selectedNameLastName: String
        ) {
            this._screenState.value =ScreenState.Loading
            //Execute add user to database
            createUser.execute({ it.either(::handleFailure, ::handleUserCreated) },
                CreateUser.Params(selectedUsername, selectedEmail, selectedPass,
                    selectedRepeatPass, selectedNameLastName), this.coroutineScope
            )

        }

        private fun handleUserCreated(id: String) {
            this._screenState.value =
                ScreenState.Render(SignUpFragmentScreenState.UserCreatedCorrectly(id))
        }

}