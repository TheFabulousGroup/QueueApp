package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import com.qflow.main.views.viewmodels.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import kotlinx.android.synthetic.users.fragment_signup.*


class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeListeners()
        initializeObservers()
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    private fun initializeListeners() {
        accept_signUp.setOnClickListener{
            val selectedUsername = username_SignUp.text.toString()
            val selectedPass = password.text.toString()
            val selectedRepeatPass = repeat_Password.text.toString()
            val selectedEmail = email_SignUp.text.toString()
            val selectedNameLastName = name_lastName.text.toString()
            viewModel.saveUserInDatabase(selectedUsername, selectedPass, selectedRepeatPass,
                selectedEmail, selectedNameLastName)
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })
    }

    private fun updateUi(screenState: ScreenState<SignUpFragmentScreenState>?) {

        when(screenState){
            ScreenState.Loading -> {}
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: SignUpFragmentScreenState) {

        when(renderState){
            is SignUpFragmentScreenState.UserCreatedCorrectly -> {
                Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }

}