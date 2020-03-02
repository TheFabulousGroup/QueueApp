package com.qflow.main.views.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.qflow.main.views.dialogs.LoginDialog
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.adapters.SignInAdapter
import com.qflow.main.views.adapters.SignInListener
import com.qflow.main.views.dialogs.SigninDialog
import com.qflow.main.views.dialogs.SignupDialog
import com.qflow.main.views.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_signup.*
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.qflow.main.views.screenstates.LoginFragmentScreenState as LoginFragmentScreenState

/**
 * Old view used for the login (pending to be deleted)
 * */

class LoginFragment : Fragment() {


    private lateinit var Login_Dialog: LoginDialog
    private lateinit var ChooseAccount: SigninDialog
    private lateinit var mySignUpDialog: SignupDialog


    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeListeners()
        return inflater.inflate(R.layout.login_fragment, container, false)
    }



    private fun initializeDialogs() {
        accept_login.setOnClickListener{
            val selectedEmail = inputEmail.text.toString()
            val selectedPass = inputPass.text.toString()
            viewModel.saveUserInDatabase(selectedEmail,selectedPass)
        }

    }

    private fun initializeListeners() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })
    }

    private fun updateUi(screenState: ScreenState<LoginFragmentScreenState>?) {

        when(screenState){
            ScreenState.Loading -> {}
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: LoginFragmentScreenState) {

        when(renderState){
            is LoginFragmentScreenState.UserCreatedCorrectly -> {
                Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })
    }






}
