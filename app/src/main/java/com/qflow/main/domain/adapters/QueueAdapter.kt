package com.qflow.main.domain.adapters

import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel

/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * */
object QueueAdapter {

    fun adapt(queueservermodel: QueueServerModel): Queue {
        return Queue(queueservermodel.name, queueservermodel.description,
            queueservermodel.capacity, queueservermodel.business_associated)
    }

}