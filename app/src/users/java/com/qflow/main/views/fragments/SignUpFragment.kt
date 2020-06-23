package com.qflow.main.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import com.qflow.main.views.viewmodels.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.qflow.main.core.Failure
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import kotlinx.android.synthetic.users.fragment_signup.*


class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListeners()
        initializeObservers()
    }

    private fun initializeListeners() {
        accept_signUp.setOnClickListener {
            val selectedUsername = username_SignUp.text.toString()
            val selectedPass = password.text.toString()
            val selectedRepeatPass = repeat_Password.text.toString()
            val selectedEmail = email_SignUp.text.toString()
            val selectedNameLastName = name_lastName.text.toString()
            viewModel.saveUserInDatabase(
                selectedUsername,
                selectedEmail,
                selectedPass,
                selectedRepeatPass,
                selectedNameLastName
            )
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    @SuppressLint("ResourceAsColor")
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                loadingComplete()
                when (failure.validationFailureType) {
                    ValidationFailureType.PASSWORDS_NOT_THE_SAME -> {
                        Toast.makeText(
                            this.context, "Passwords do not match", Toast.LENGTH_LONG
                        ).show()
                        this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }
                            ?.let {
                                password.background.setTint(it)
                                repeat_Password.background.setTint(it)
                            }
                    }
                }
            }
        }
    }

    private fun updateUI(screenState: ScreenState<SignUpFragmentScreenState>?) {

        when (screenState) {
            ScreenState.Loading -> {
                loading()
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: SignUpFragmentScreenState) {
        loadingComplete()

        when (renderState) {
            is SignUpFragmentScreenState.UserCreatedCorrectly -> {

                view?.findNavController()?.navigate(R.id.action_SignUpFragment_to_navigation_home)
            }
        }

    }


    private fun loading() {
        //Make sure you've added the loader to the view
        loading_bar.visibility = View.VISIBLE
    }

    private fun loadingComplete() {
        loading_bar.visibility = View.INVISIBLE
    }

}