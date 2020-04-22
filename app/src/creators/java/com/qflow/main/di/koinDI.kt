package com.qflow.main.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.server.ApiService
import com.qflow.main.repository.QueueRepository
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.creator.FetchAdminActiveQueues
import com.qflow.main.usecases.creator.FetchAdminNotActiveQueues
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.usecases.queue.FetchQueueById
import com.qflow.main.usecases.user.CreateAdmin
import com.qflow.main.usecases.user.CreateUser
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.utils.Constants
import com.qflow.main.views.viewmodels.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Our great dependency injector, gets whatever we want wherever we desire
 * */
val retrofitModule = module {

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

    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { CreateQueueViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { InfoQueueViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }

}

val useCaseModule = module {

    factory { CreateUser(get(),get()) }
    factory { CreateAdmin(get(),get()) }
    factory { LoginCase(get(),get()) }
    factory { CreateQueue(get()) }
    factory { FetchAdminActiveQueues(get()) }
    factory { FetchQueueById(get()) }
    factory { FetchAdminNotActiveQueues(get()) }


}

val dataModule = module {

    single { AppDatabase.getInstance(get()) }
    single { SharedPrefsRepository(get()) }

}