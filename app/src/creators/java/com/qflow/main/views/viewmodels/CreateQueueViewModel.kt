package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.views.screenstates.CreateQueueScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class CreateQueueViewModel (

    private val createQueue: CreateQueue
    ) : BaseViewModel(), KoinComponent {

        private val _screenState: MutableLiveData<ScreenState<CreateQueueScreenState>> = MutableLiveData()
        val screenState: LiveData<ScreenState<CreateQueueScreenState>>
        get() = _screenState

        private var job = Job()
        private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

        fun createQueueInDatabase(
            nameCreateQueue: String,
            businessAssociated: String,
            queueDescription: String,
            capacity: Int
        ) {

            //Execute create queue
            createQueue.execute({ it.either(::handleFailure, ::handleUserCreated) },
                CreateQueue.Params(nameCreateQueue, businessAssociated, queueDescription,
                    capacity), this.coroutineScope
            )

        }

        private fun handleUserCreated(id: String) {
            this._screenState.value =
                ScreenState.Render(CreateQueueScreenState.QueueCreatedCorrectly(id))
        }


    }
