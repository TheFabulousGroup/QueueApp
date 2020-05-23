package com.qflow.main.adapters

import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.adapters.QueueAdapter.jsonStringToQueue
import com.qflow.main.domain.adapters.QueueAdapter.jsonStringToQueueList
import com.qflow.main.domain.server.models.QueueServerModel import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.junit.Assert
import org.junit.Test
import org.koin.ext.getOrCreateScope
import java.util.*
import kotlin.collections.HashMap

class QueueAdapterTest {
    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    @Test
    fun testJsonStringToQueue(){
        val JSONMock =
                "   {\n" +
                "      \"id\":1,\n" +
                "      \"name\":\"QueueOne\",\n" +
                "      \"description\":\"DescOne\",\n" +
                "      \"joinId\":1,\n" +
                "      \"dateCreated\":\"2013-02-18 11:32:21\",\n" +
                "      \"dateFinished\":\"2013-02-18 11:32:21\",\n" +
                "      \"capacity\":100,\n" +
                "      \"currentPos\":1,\n" +
                "      \"isLock\":false,\n" +
                "      \"businessAssociated\":\"BusinessOne\"\n" +
                "   }\n"

        val queueResult = jsonStringToQueue(JSONMock)
        Assert.assertEquals(queueResult.name, "QueueOne")
        Assert.assertEquals(queueResult.description, "DescOne")
        Assert.assertEquals(queueResult.joinId, 1)
    }

    @Test
    fun testJsonStringToQueueList(){
        val JSONMock =
            "[\n" +
                    "   {\n" +
                    "      \"id\":1,\n" +
                    "      \"name\":\"QueueOne\",\n" +
                    "      \"description\":\"DescOne\",\n" +
                    "      \"joinId\":1,\n" +
                    "      \"dateCreated\":\"2013-02-18 11:32:21\",\n" +
                    "      \"dateFinished\":\"2013-02-18 11:32:21\",\n" +
                    "      \"capacity\":100,\n" +
                    "      \"currentPos\":1,\n" +
                    "      \"isLock\":false,\n" +
                    "      \"businessAssociated\":\"BusinessOne\"\n" +
                    "   },\n" +
                    "   {\n" +
                    "      \"id\":2,\n" +
                    "      \"name\":\"Queuet\",\n" +
                    "      \"description\":\"Desct\",\n" +
                    "      \"joinId\":2,\n" +
                    "      \"dateCreated\":\"2013-02-18 11:32:21\",\n" +
                    "      \"dateFinished\":\"2013-02-18 11:32:21\",\n" +
                    "      \"capacity\":100,\n" +
                    "      \"currentPos\":1,\n" +
                    "      \"isLock\":false,\n" +
                    "      \"businessAssociated\":\"Businesst\"\n" +
                    "   }\n" +
            "]"

        val queueResult = jsonStringToQueueList(JSONMock)

        Assert.assertEquals(queueResult[0].name, "QueueOne")
        Assert.assertEquals(queueResult[0].description, "DescOne")
        Assert.assertEquals(queueResult[0].joinId, 1)
        Assert.assertEquals(queueResult[1].name, "Queuet")
        Assert.assertEquals(queueResult[1].description, "Desct")
        Assert.assertEquals(queueResult[1].joinId, 2)
        Assert.assertEquals(queueResult[1].businessAssociated, "Businesst")

    }

}