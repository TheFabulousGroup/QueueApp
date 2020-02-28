package com.qflow.main.domain.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

/**
 * Example class for requests (Not used yet)
 * */
interface ApiService {


    companion object Factory {

        const val USERS_ENDPOINT = "users/"

        const val PARAM_PROFILE_CODE = "profileCode"
        const val PARAM_DEVICE_CODE = "deviceCode"

        const val PUT_USER_EDIT = "profiles/{$PARAM_PROFILE_CODE}"

        const val POST_SIGNUP = "signup"
        const val POST_SIGNIN = "login"
        const val POST_DATA = "profiles/{$PARAM_PROFILE_CODE}/devices/{$PARAM_DEVICE_CODE}/data"

    }


    /*USER*/

    @GET("profiles/{profileCode}")
    fun getProfile(@Path("profileCode") profileCode: Int): Call<String>

    @DELETE("profiles/{profileCode}")
    fun deleteProfile(@Path("profileCode") profileCode: Int): Call<String>

//    @POST(ACCOUNTS_ENDPOINT + POST_SIGNUP)
//    fun postSignUp(@Body body: String): Call<String>
//
//    @FormUrlEncoded
//    @POST(ACCOUNTS_ENDPOINT + POST_SIGNIN)
//    fun postSignin(@FieldMap body: Map<String, String>): Call<String>
//
//    @FormUrlEncoded
//    @POST(PROFILES_ENDPOINT)
//    fun postProfile(@FieldMap body: Map<String, String>): Call<String>
//
//    @FormUrlEncoded
//    @PUT(PUT_USER_EDIT)
//    fun putUserEdit(@Path(PARAM_PROFILE_CODE) profileCode: Int, @FieldMap body: Map<String, String>): Call<String>
//
//    @Multipart
//    @retrofit2.http.PUT(PUT_USER_EDIT)
//    fun putUploadAvatar(@Path(PARAM_PROFILE_CODE) profileCode: Int, @Part map: MultipartBody.Part): Call<String>

//    @FormUrlEncoded
//    @POST(PROFILES_ENDPOINT + SUPERVISORS_ENDPOINT)
//    fun postSupervisor(@FieldMap body: Map<String, String>): Call<String>
//
//    @POST(POST_ACCEPT_RECALIBRATION)
//    fun postAcceptRecalibration(@Path(PARAM_PROFILE_CODE) profileCode: Int): Call<String>
//
//    @POST(POST_ACCEPT_LOGOUT)
//    fun postAcceptLogout(@Path(PARAM_PROFILE_CODE) profileCode: Int): Call<String>
//
//
//    @POST(POST_LOGOUT_PETITION)
//    fun postLogoutPetition(@Path(PARAM_PROFILE_CODE) profileCode: Int): Call<String>
//
//    @POST(POST_RECALIBRATION_PETITION)
//    fun postRecalibrationPetition(@Path(PARAM_PROFILE_CODE) profileCode: Int): Call<String


}

//class HeaderInterceptor : Interceptor, KoinComponent {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        var request = chain.request()
//        var requestBuilder: Request.Builder
//
//
////        var sessionCode: String? = user?.secret
////        var sessionUser: Int? = user?.userCode
//
//
//
//        requestBuilder = request.newBuilder()
////                .addHeader("Secret", Constants.CREDENTIALS.SECRET)
////                .addHeader("Token", Constants.CREDENTIALS.TOKEN)
//                .addHeader("API-apptype", "android")
//                .addHeader("Content-Type", "application/json")
//
////        if (sessionUser != null && sessionCode != null)
////            requestBuilder.addHeader("SESSION-GI", "$sessionUser.$sessionCode")
//
//
////UNCOMMENT TO SEE WHAT ARE WE SENDING
//        Log.i("REQUEST",
//                String.format("Sending request %s on %s %s", request.url(), chain.connection(), request.headers()))
//
//        val response = chain.proceed(requestBuilder.build())
//
////UNCOMMENT TO SEE WHAT ARE WE RECEIVING
//        Log.i("REQUEST",
//                String.format("Received response for %s, headers: %s", request.url(), response.body()))
//
//        val body = ResponseBody.create(response.body()?.contentType(), response.body()!!.string())
//        return response.newBuilder().body(body).build()
//    }
//}
//
