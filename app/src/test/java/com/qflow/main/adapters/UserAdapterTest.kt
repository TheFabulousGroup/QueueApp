package com.qflow.main.adapters;

import com.qflow.main.domain.adapters.UserAdapter.jsonStringToUserDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.junit.Assert
import org.junit.Test

public class UserAdapterTest {

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    @Test
    fun testJsonStringToUserDTO(){
        val JSONMock =
                "   {\n" +
                        "      \"username\":\"Pepe\",\n" +
                        "      \"email\":\"pepe@gmail.com\",\n" +
                        "      \"token\":\"kilo\"\n" +
                        "   }\n"

        val userResult = jsonStringToUserDTO(JSONMock)
        Assert.assertEquals(userResult.email, "pepe@gmail.com")
        Assert.assertEquals(userResult.token, "kilo")
        Assert.assertEquals(userResult.username, "Pepe")
    }
    /*
    * ´"username":"pepevilluela","email":"pepe.example","token":"32345"}
    *
    *
    * */
}
