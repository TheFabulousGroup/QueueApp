package com.qflow.main.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.viewmodels.LoginViewModel
import kotlinx.android.synthetic.creators.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.qflow.main.views.screenstates.LoginFragmentScreenState

/**
 * Old view used for the login (pending to be deleted)
 * */

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.testFeature()
        initializeListeners()
        initializeObservers()
    }

    private fun initializeListeners() {
        initializeButtons()
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {
        btn_signIn.setOnClickListener {
            val email = inputEmail.text.toString()
            val pass = inputPass.text.toString()
            viewModel.login(pass, email)
        }
        btn_signUp.setOnClickListener {
            view.let {view?.findNavController()!!
                .navigate(LoginFragmentDirections
                    .actionLoginFragmentToSignUpFragment()) }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY -> {
                        Toast.makeText(
                            this.context, "Email or password empty", Toast.LENGTH_LONG
                        ).show()
                        this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }?.let {
                            inputPass.background.setTint(it)
                            inputEmail.background.setTint(it)
                        }
                    }
                }
            }
            is Failure.ServerException ->
                Toast.makeText(this.context, getString(R.string.login_not_successful), Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateUI(screenState: ScreenState<LoginFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: LoginFragmentScreenState) {
        when (renderState) {
            is LoginFragmentScreenState.LoginSuccessful -> {
                view?.let {
                    view?.findNavController()!!
                        .navigate(
                            LoginFragmentDirections.actionLoginFragmentToProfileFragment(renderState.id)
                        )
                }
            }
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }


}
