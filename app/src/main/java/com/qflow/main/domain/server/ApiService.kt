package com.qflow.main.domain.server

import android.util.Log
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
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

    val prefs : SharedPrefsRepository
    companion object Factory {
        const val PARAM_QUEUE_ID = "idQueue"
        const val HEADER_IS_ADMIN = "isAdmin"
        const val HEADER_EMAIL = "mail"
        const val HEADER_PASS = "password"
        const val HEADER_TOKEN = "token"
        const val PARAM_LOCKED = "locked"
        const val PARAM_EXPAND = "expand"

        const val POST_JOIN_QUEUE = "queue/join"
        const val POST_CREATE_QUEUE = "qflow/queues/"
        const val GET_QUEUE_USERID = "qflow/queues/byIdUser/"
        const val GET_QUEUE_QUEUEID = "qflow/queues/byIdQueue/"
        const val POST_CREATE_USER = "qflow/user/"
        const val PUT_LOGIN_USER = "qflow/user/"

    }
    @Headers("Content-type: application/json")
    @GET(GET_QUEUE_USERID)
    fun getQueuesByUser(
        @Header(HEADER_TOKEN) token: String,
        @Query(PARAM_EXPAND) expand: String?,
        @Query(PARAM_LOCKED) locked: Boolean?
    ) : Call<String>

    @GET(GET_QUEUE_QUEUEID)
    fun getQueueByQueueId(@Path(PARAM_QUEUE_ID) idQueue: Int): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_CREATE_USER)
    fun postCreateUser(@Body body: String, @Header(HEADER_IS_ADMIN) admin: Boolean): Call<String>

    @Headers("Content-type: application/json")
    @PUT(PUT_LOGIN_USER)
    fun postLoginUser(
        @Header(HEADER_IS_ADMIN) admin: Boolean,
        @Header(HEADER_EMAIL) email: String,
        @Header(HEADER_PASS) password:String
    ): Call<String>

    @Headers("Content-type: application/json")
    @POST(POST_CREATE_QUEUE)
    fun postQueue(@Header(HEADER_TOKEN) token:String, @Body body: String): Call<String>

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

