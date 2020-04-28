package com.qflow.main.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.repository.QueueRepository
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.creator.FetchAdminActiveQueues
import com.qflow.main.usecases.creator.FetchAdminNotActiveQueues
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.usecases.queue.FetchQueueById
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.usecases.user.CreateAdmin
import com.qflow.main.usecases.user.CreateUser
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.viewmodels.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient
/**
 * Our great dependency injector, gets whatever we want wherever we desire
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

val fireBaseModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFunctions.getInstance() }
}

val userModule = module {

    single<UserRepository> { UserRepository.General(get(), get(), get(), get()) }
    single<QueueRepository> { QueueRepository.General(get(), get(), get(), get(), get()) }

    single { UserAdapter }
    single { QueueAdapter }

    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { JoinQueueViewModel(get()) }
    viewModel { HomeViewModel()}
    viewModel { SplashScreenViewModel(get()) }

}

val useCaseModule = module {

    factory { CreateUser(get(), get()) }
    factory { CreateAdmin(get(), get()) }
    factory { LoginCase(get(), get()) }
    factory { CreateQueue(get()) }
    factory { FetchAdminActiveQueues(get()) }
    factory { FetchQueueById(get()) }
    factory { JoinQueue(get()) }
    factory { FetchAdminNotActiveQueues(get()) }


}

val dataModule = module {

    single { AppDatabase.getInstance(get()) }
    single { SharedPrefsRepository(get()) }

}