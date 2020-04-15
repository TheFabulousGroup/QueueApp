package com.qflow.main.domain.adapters

import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel

/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * */
object QueueAdapter {

    fun queueSMToQueue(queueServerModel: QueueServerModel): Queue {
        return Queue(
            queueServerModel.name, queueServerModel.description,
            queueServerModel.capacity, queueServerModel.business_associated, queueServerModel.id,
            queueServerModel.date_created, queueServerModel.date_finished, queueServerModel.is_active
        )
    }

    fun queueSMListToQueueList(queueSMList: List<QueueServerModel>): List<Queue> {
        val resultList = ArrayList<Queue>()
        queueSMList.forEach {
            resultList.add(queueSMToQueue(it))
        }
        return resultList
    }

}