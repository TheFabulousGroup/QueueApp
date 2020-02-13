
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.queueapp.main.repository.UserRepository
import com.queueapp.main.views.viewmodels.LoginViewModel
import com.queueapp.main.views.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.visionapp.myopia.kotlin.data.cache.UserAccountCache
import org.visionapp.myopia.kotlin.data.server.*
import org.visionapp.myopia.kotlin.domain.mapper.Usermapper
import org.visionapp.myopia.kotlin.domain.models.ServerSharedPreferencesManager
import org.visionapp.myopia.kotlin.domain.repository.CenterRepository
import org.visionapp.myopia.kotlin.domain.repository.CoreRepository
import org.visionapp.myopia.kotlin.domain.repository.UserRepository
import org.visionapp.myopia.kotlin.domain.uc.center.AskCenter
import org.visionapp.myopia.kotlin.domain.uc.center.LocationUser
import org.visionapp.myopia.kotlin.domain.uc.center.ObtainCenter
import org.visionapp.myopia.kotlin.domain.uc.center.ObtainCenters
import org.visionapp.myopia.kotlin.domain.uc.profile.*
import org.visionapp.myopia.kotlin.domain.uc.user.*
import org.visionapp.myopia.kotlin.presentation.access.*
import org.visionapp.myopia.kotlin.presentation.barscan.BarScanActivityViewModel
import org.visionapp.myopia.kotlin.presentation.center.directory.CenterDirectoryFragmentViewModel
import org.visionapp.myopia.kotlin.presentation.dashboard.tabs.dashboard.DashboardOverviewViewModel
import org.visionapp.myopia.kotlin.presentation.dialogs.NewProfileViewModel
import org.visionapp.myopia.kotlin.presentation.login.LoginParentFragmentViewModel
import org.visionapp.myopia.kotlin.presentation.main.MainActivityViewModel
import org.visionapp.myopia.kotlin.presentation.signup.SignUpFragmentViewModel
import org.visionapp.myopia.kotlin.utils.Constants
import retrofit2.converter.scalars.ScalarsConverterFactory

val retrofitModule = module {

//
//    single {
//        OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(HeaderInterceptor())
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS).build()
//    }
//
//    //API Service
//    single(named("application")) {
//        Retrofit.Builder()
//                .baseUrl(Constants.END_POINT_URL)
//                .client(get())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build().create(ApiService::class.java)
//    }
//
//    single(named("core")) {
//        Retrofit.Builder()
//                .baseUrl(Constants.END_POINT_TOKEN)
//                .client(get())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build().create(ApiService::class.java)
//    }
}

val socialLogin = module {

//    single { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(Constants.GOOGLE.GOOGLE_OAUTH_CLIENT)
//            .requestEmail()
//            .build()
//    }

}


//
//val networkModule = module{
//
//    single{ NetworkHandler(get()) }
//}
//
//val dataModule = module {
//    single { ServerSharedPreferencesManager.getInstance(get()) }
//    single { UserAccountCache(timeInMin = 10L) }
//}
//
//
//val mapModule = module {
//    single { Usermapper }
//}

val useCaseModule = module {

    factory { AssignSupervisor(get()) }

}


val coreModule = module {
    single<CoreRepository> { CoreRepository.Network(get(),get(named("core")), ServerResponseMapper) }
}
val userModule = module {

    single<UserRepository> { UserRepository.Network(get(named("application")), ServerResponseMapper, get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get(),get()) }

}
