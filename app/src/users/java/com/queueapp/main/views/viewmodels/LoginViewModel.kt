package com.queueapp.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.queueapp.main.database.user.User
import com.queueapp.main.database.user.AppDatabase
import com.queueapp.main.domain.local.models.ViewStates
import com.queueapp.main.domain.local.models.ViewStatesMessageTypes
import kotlinx.coroutines.*


class LoginViewModel (private val appDatabase: AppDatabase) : ViewModel() {

    private val _currentUser: MutableLiveData<Long> = MutableLiveData()
    val currentUser: LiveData<Long>
        get() = _currentUser

    private val _screenState: MutableLiveData<ViewStates> = MutableLiveData()
    val screenState: LiveData<ViewStates>
        get() = _screenState

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun saveUserInDatabase(
        username: String,
        selectedPass: String,
        selectedMail: String
    ){
        val user = User(
            password = selectedPass,
            username = username,
            mail = selectedMail
        )
        uiScope.launch {
            executeAddUserToDatabase(user)
        }
    }

    fun getUsersFromDatabase(){

        uiScope.launch {
            executeGetAllUsers()
        }

    }

    fun initiateNormalLogin(selectedUser: String, selectedPass: String) {
        uiScope.launch {
            executeCheckIfUserExists(selectedUser, selectedPass)
        }
    }

    private suspend fun executeCheckIfUserExists(selectedUser: String, selectedPass: String) {
        return withContext(Dispatchers.IO){
            val user = appDatabase.userDatabaseDao.correctUser(selectedUser, selectedPass)
            if( user != null) {
                _currentUser.postValue(user.userId)
                _screenState.postValue(ViewStates(200, ViewStatesMessageTypes.USER_ASSIGNED))
            }
            else{
                _screenState.postValue(ViewStates(400, ViewStatesMessageTypes.LOGIN_NOT_SUCCESSFUL))
            }
        }
    }

    private suspend fun executeAddUserToDatabase(user: User) {
        return withContext(Dispatchers.IO) {
            appDatabase.userDatabaseDao.insert(user)
            val id = appDatabase.userDatabaseDao.correctUser(user.username, user.password)?.userId
            if(id != null) {
                _currentUser.postValue(id)
                _screenState.postValue(ViewStates(200, ViewStatesMessageTypes.USER_ASSIGNED))
            }
            else
                _screenState.postValue(ViewStates(400, ViewStatesMessageTypes.SIGN_IN_FAILED))
        }
    }

    private suspend fun executeGetAllUsers(){
        return withContext(Dispatchers.IO) {
            //TODO [RecyclerView]ver que hacer con la lista de usuarios cuando la consigamos
//            userDatabase.userDatabaseDao.getAllUsers()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
