package com.qflow.main.domain.server

import android.util.Log
import com.qflow.main.domain.local.SharedPrefsRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.Call
import retrofit2.http.*
import java.io.IOException

interface ApiService {


    companion object Factory {
        const val PARAM_QUEUE_ID = "queueId"

        const val POST_JOIN_QUEUE = "queue/join"
        const val POST_CREATE_QUEUE = "queue/create"
        const val GET_QUEUES = "queue/"
        const val GET_QUEUE = "queue/{$PARAM_QUEUE_ID}"
        const val POST_CREATE_USER = "user/create"
        const val POST_LOGIN_USER = "user/"

    }

    @GET(GET_QUEUES)
    fun getQueues(): Call<String>

    @GET(GET_QUEUE)
    fun getQueue(@Path(PARAM_QUEUE_ID) queueId: Int): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_CREATE_USER)
    fun postCreateUser(@Body body: String): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_LOGIN_USER)
    fun postLoginUser(@Body body: String): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_CREATE_QUEUE)
    fun postQueue(@Body body: String): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_JOIN_QUEUE)
    fun postJoinQueue(@Body body: String): Call<String>
}

class HeaderInterceptor : Interceptor, KoinComponent {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder: Request.Builder
        val serverSharedPreferencesManager: SharedPrefsRepository = get()

        val userToken = serverSharedPreferencesManager.getUserToken()

        requestBuilder = request.newBuilder()
            .addHeader("Authorization", "Bearer $userToken")

//UNCOMMENT TO SEE WHAT ARE WE SENDING
        Log.i(
            "REQUEST",
            String.format(
                "Sending request %s on %s %s",
                request.url(),
                chain.connection(),
                request.headers()
            )
        )

        val response = chain.proceed(requestBuilder.build())

            //UNCOMMENT TO SEE WHAT ARE WE RECEIVING
        Log.i(
            "REQUEST",
            String.format("Received response for %s, headers: %s", request.url(), response.body())
        )

        val body = ResponseBody.create(response.body()?.contentType(), response.body()!!.string())
        return response.newBuilder().body(body).build()
    }
}

