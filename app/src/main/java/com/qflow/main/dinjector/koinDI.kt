package com.qflow.main.dinjector

import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.server.ApiService
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.utils.Constants
import com.qflow.main.views.viewmodels.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
//import com.google.firebase.firestore.FirebaseFirestore
/**
 * Our great dependency injector, gets whatever we want wherever we desire
 * */
val retrofitModule = module {

    //API Service
    single(named("application")) {
        Retrofit.Builder()
                .baseUrl(Constants.END_POINT_URL)
                .client(get())
                .build().create(ApiService::class.java)
    }
}


val userModule = module {

    single<UserRepository> { UserRepository.Local(get(), UserAdapter) }

    viewModel { LoginViewModel(get()) }
//    viewModel { ProfileViewModel(get(),get()) }

}

val useCaseModule = module {

    factory { LoginCase(get()) }

}

val dataModule = module {

    single { AppDatabase.getInstance(get()) }

}
//val fireBaseModule = module {
//
//    single {  FirebaseFirestore.getInstance() }
//
//}