package org.visionapp.myopia.kotlin.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import retrofitModule
import useCaseModule
import userModule
import java.util.*
import java.util.concurrent.TimeUnit


/*
* @author  Iván Fernández Rico, Globalincubator
*/
class App : Application(), KoinComponent, Application.ActivityLifecycleCallbacks
{


    private lateinit var handlerPost: Handler

    override fun onCreate() {
        super.onCreate()


        registerActivityLifecycleCallbacks(this)


        handlerPost = Handler()


        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(retrofitModule,dataModule,useCaseModule,userModule))
        }

        //Part reserved to notifications
//        Notifications.init(applicationContext, Device.load())
//        initLifeCycleOwner()


    }

//    This function might be interesting to receiveNotifications
//    private fun initLifeCycleOwner()
//    {
//        ProcessLifecycleOwner.get()
//            .lifecycle
//            .addObserver(
//                ForegroundNotificationResponseListenerOwner()
//                    .also { appObserver = it })
//    }




    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }


}