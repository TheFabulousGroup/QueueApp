package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import com.qflow.main.views.viewmodels.SignUpViewModel
import kotlinx.android.synthetic.creators.fragment_signup.*
import org.koin.androidx.viewmodel.ext.android.viewModel



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
            val selectedCIF=_CIF.text.toString()
            val selectedUsername = username_SignUp.text.toString()
            val selectedPass = password.text.toString()
            val selectedRepeatPass = repeat_Password.text.toString()
            val selectedEmail = email_SignUp.text.toString()
            val selectedNameLastName = name_lastName.text.toString()
            viewModel.saveUserInDatabase(
                selectedCIF,selectedUsername, selectedPass, selectedRepeatPass,
                selectedEmail, selectedNameLastName
            )
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.PASSWORDS_NOT_THE_SAME -> {
                        //TODO añadir aqui que hacer cuando el validador de fallo

                    }
                }
            }
        }
    }

    private fun updateUI(screenState: ScreenState<SignUpFragmentScreenState>?) {

        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: SignUpFragmentScreenState) {

        when (renderState) {
            is SignUpFragmentScreenState.UserCreatedCorrectly -> {
                //Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
                view?.let {
                    view?.findNavController()!!.
                    navigate(LoginFragmentDirections
                                .actionLoginFragmentToProfileFragment(renderState.id)
                        )
                }
            }
        }

    }

}