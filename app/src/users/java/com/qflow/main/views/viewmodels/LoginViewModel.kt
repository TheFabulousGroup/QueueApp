package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.screenstates.LoginFragmentScreenState

/**
 * Viewmodel of the LoginFragment, it connects with the usecases
 *
 * */
class LoginViewModel(
    private val userLogin: LoginCase
) : BaseViewModel(), KoinComponent {

    private val _currentUser: MutableLiveData<Long> = MutableLiveData()
    val currentUser: LiveData<Long>
        get() = _currentUser

    private val _screenState: MutableLiveData<ScreenState<LoginFragmentScreenState>> = MutableLiveData()
    val screenState: LiveData<ScreenState<LoginFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun saveUserInDatabase(
        username: String,
        selectedPass: String,
        selectedMail: String
    ) {

        //Execute add user to database
        saveUserDatabase.execute({ it.either(::handleFailure, ::handleUserCreated) },
            SaveUserInDatabase.Params(username, selectedPass, selectedMail), this.coroutineScope
        )

    }

    private fun handleUserCreated(id: Long) {
        this._screenState.value =
            ScreenState.Render(LoginFragmentScreenState.UserCreatedCorrectly(id))
    }

//    fun getUsersFromDatabase() {
//
//        uiScope.launch {
//            executeGetAllUsers()
//        }
//
//    }
//
//    fun initiateNormalLogin(selectedUser: String, selectedPass: String) {
//        uiScope.launch {
//            executeCheckIfUserExists(selectedUser, selectedPass)
//        }
//    }
//
//    private suspend fun executeCheckIfUserExists(selectedUser: String, selectedPass: String) {
//        return withContext(Dispatchers.IO) {
//            val user = appDatabase.userDatabaseDao.correctUser(selectedUser, selectedPass)
//            if (user != null) {
//                _currentUser.postValue(user.userId)
//                _screenState.postValue(ViewStates(200, ViewStatesMessageTypes.USER_ASSIGNED))
//            } else {
//                _screenState.postValue(ViewStates(400, ViewStatesMessageTypes.LOGIN_NOT_SUCCESSFUL))
//            }
//        }
//    }
//
//    private suspend fun executeAddUserToDatabase(user: User) {
//        return withContext(Dispatchers.IO) {
//            appDatabase.userDatabaseDao.insert(user)
//            val id = appDatabase.userDatabaseDao.correctUser(user.username, user.password)?.userId
//            if (id != null) {
//                _currentUser.postValue(id)
//                _screenState.postValue(ViewStates(200, ViewStatesMessageTypes.USER_ASSIGNED))
//            } else
//                _screenState.postValue(ViewStates(400, ViewStatesMessageTypes.SIGN_IN_FAILED))
//        }
//    }
//
//    private suspend fun executeGetAllUsers() {
//        return withContext(Dispatchers.IO) {
////            userDatabase.userDatabaseDao.getAllUsers()
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
