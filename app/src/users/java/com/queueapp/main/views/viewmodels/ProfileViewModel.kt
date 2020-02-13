package com.queueapp.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.queueapp.main.database.user.User
import com.queueapp.main.database.user.UserDatabase
import kotlinx.coroutines.*
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val userId: Long, val userDatabase: UserDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userId, userDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ProfileViewModel(
    private val userId: Long,
    private val userDatabase: UserDatabase
) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    init{
        uiScope.launch {
            executeGetCurrentUser()
        }
    }

    private suspend fun executeGetCurrentUser() {
        return withContext(Dispatchers.IO) {
            _currentUser.postValue(userDatabase.userDatabaseDao.get(userId))
        }
    }

}
