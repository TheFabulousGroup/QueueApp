package com.qflow.main.servermodels

import com.qflow.main.domain.server.models.QueueServerModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.junit.Test
import java.util.*
import kotlin.collections.HashMap

class QueueServerModelTest {
    /*private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    //Llamar a mapListToObjectList con Mock de string
    @Test
    fun testCreateMap() {
        val date_cr = Date(2010, 4, 8, 11, 32, 46)
        val date_fn = Date(2010, 4, 8, 22, 32, 46)

        val correctMap = HashMap<String, String>()
        correctMap["name"] = "QueueName"
        correctMap["description"] = "Descrip"
        correctMap["capacity"] = 100.toString()
        correctMap["business_associated"] = "bsnss"
        correctMap["date_created"] = date_cr.toString()
        correctMap["date_finished"] = date_fn.toString()
        correctMap["is_locked"] = "QueueName"

        val queueServerModel: QueueServerModel = QueueServerModel(
            "QueueName", "Descrip", 100, "bsnss",
            date_cr, date_fn
        )

        val mapToTest = queueServerModel.createMap()

        assertEquals(mapToTest, correctMap)
    }

    @Test
    fun testMapListToObjectList() {
        val JSONMock = "[\n" +
                "   {\n" +
                "      \"business_associated\":\"BusinessOne\",\n" +
                "      \"capacity\":100,\n" +
                "      \"date_created\":\"\",\n" +
                "      \"date_finished\":\"\",\n" +
                "      \"description\":\"DescOne\",\n" +
                "      \"is_locked\":false,\n" +
                "      \"name\":\"QueueOne\"\n" +
                "   },\n" +
                "   {\n" +
                "      \"business_associated\":\"BusinessTwo\",\n" +
                "      \"capacity\":100,\n" +
                "      \"date_created\":\"\",\n" +
                "      \"date_finished\":\"\",\n" +
                "      \"description\":\"DescTwo\",\n" +
                "      \"is_locked\":false,\n" +
                "      \"name\":\"QueueTwo\"\n" +
                "   }\n" +
                "]"

        val qsmOne = QueueServerModel(
            "QueueOne", "DescOne", 100,
            "BusinessOne", null, null, false
        )
        val qsmTwo = QueueServerModel(
            "QueueTwo", "DescTwo", 100,
            "BusinessTwo", null, null, false
        )

        val queueServerModelArrayTest = listOf(qsmOne, qsmTwo)
        val result = mapListToObjectList(JSONMock)


        assertEquals(queueServerModelArrayTest[0].name  , result[0].name)     //Assert del nombre, comprobamos debuggeando
    }*/

}