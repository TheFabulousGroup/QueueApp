package com.qflow.main.di

import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.server.ApiService
import com.qflow.main.domain.server.HeaderInterceptor
import com.qflow.main.repository.QueueRepository
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.usecases.queue.FetchQueueById
import com.qflow.main.usecases.queue.FetchQueuesByUser
import com.qflow.main.usecases.user.CreateAdmin
import com.qflow.main.usecases.user.CreateUser
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.utils.Constants
import com.qflow.main.views.viewmodels.*
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Our dependency injector, gets whatever we want wherever we desire
 * */
val retrofitModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
    }

    //API Service
    single {
        Retrofit.Builder()
            .baseUrl(Constants.END_POINT_URL)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}

val userModule = module {

    single<UserRepository> { UserRepository.General(get()) }
    single<QueueRepository> { QueueRepository.General(get(), get(), get()) }

    single { UserAdapter }
    single { QueueAdapter }

    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { CreateQueueViewModel(get()) }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { InfoQueueViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }

}

val useCaseModule = module {

    factory { CreateUser(get(),get()) }
    factory { CreateAdmin(get(),get()) }
    factory { LoginCase(get(),get()) }
    factory { CreateQueue(get(), get()) }
    factory { FetchQueuesByUser(get(), get()) }
    factory { FetchQueueById(get()) }


}

val dataModule = module {

    single { AppDatabase.getInstance(get()) }
    single { SharedPrefsRepository(get()) }

}