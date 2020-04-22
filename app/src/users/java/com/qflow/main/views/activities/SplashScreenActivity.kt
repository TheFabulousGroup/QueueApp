package com.qflow.main.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.SplashScreenScreenState
import com.qflow.main.views.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val mViewModel: SplashScreenViewModel by viewModel()
    private val SPLASH_TIME_OUT:Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeObservers()
        Handler().postDelayed({
            mViewModel.checkIfUserIsLogged()
        }, SPLASH_TIME_OUT)
    }

    private fun initializeObservers() =
        mViewModel.screenState.observe(::getLifecycle, ::updateUI)


    private fun renderScreenState(renderState: SplashScreenScreenState) {
        when (renderState) {
            SplashScreenScreenState.UserIsLogged ->
                startActivity(Intent(this, HomeActivity::class.java))
            SplashScreenScreenState.UserIsNotLogged ->
                startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun updateUI(screenState: ScreenState<SplashScreenScreenState>?) {
        when (screenState) {
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }
}
