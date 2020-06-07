package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.queue.FetchQueueById
import com.qflow.main.views.screenstates.InfoQueueScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class ManagementQueueViewModel(): BaseViewModel(), KoinComponent {

        private val _screenState: MutableLiveData<ScreenState<InfoQueueScreenState>> =
            MutableLiveData()
        val screenState: LiveData<ScreenState<InfoQueueScreenState>>
            get() = _screenState

        private var job = Job()
        private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

        private lateinit var queueObtained: Queue

        fun whatever(idQueue: Int) {

        }

        private fun whatever2(queue: Queue) {

        }

    }