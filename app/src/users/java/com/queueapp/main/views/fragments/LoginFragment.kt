package com.queueapp.main.views.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.queueapp.main.views.dialogs.LoginDialog
import com.queueapp.main.R
import com.queueapp.main.database.user.AppDatabase
import com.queueapp.main.domain.local.models.ViewStatesMessageTypes.*
import com.queueapp.main.views.adapters.SignInAdapter
import com.queueapp.main.views.adapters.SignInListener
import com.queueapp.main.views.dialogs.SigninDialog
import com.queueapp.main.views.dialogs.SignupDialog
import com.queueapp.main.views.viewmodels.LoginViewModel
import com.queueapp.main.views.viewmodels.LoginViewModelFactory
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    private lateinit var myLoginDialog: LoginDialog
    private lateinit var myChooseAccountDialog: SigninDialog
    private lateinit var mySignUpDialog: SignupDialog

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = LoginViewModelFactory(AppDatabase.getInstance(activity!!.application))
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.login_fragment, container, false)
    }



    private fun initializeDialogs() {
        myLoginDialog = LoginDialog(viewModel)
        myChooseAccountDialog = SigninDialog(viewModel)
        mySignUpDialog = SignupDialog(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeDialogs()
        loginApprender.setOnClickListener {
            openApprenderDialog()
        }
        signUp.setOnClickListener {
//            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment("QRlog"))
            openCreateAccountDialog()
        }
        signIn.setOnClickListener {
            openChooseAccountDialog()
        }
        val adapter = SignInAdapter(SignInListener { user ->
//            sleepTrackerViewModel.onSleepNightClicked(nightId)
            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment(user.userId))
        })
        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            when (it.screen){
                USER_ASSIGNED -> {
                    view?.let {view?.findNavController()!!.navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment(viewModel.currentUser.value!!)) }
                }
                LOGIN_NOT_SUCCESSFUL -> {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }
                SIGN_IN_FAILED -> {
                    Toast.makeText(context, "Signup Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun openChooseAccountDialog() {
        activity?.let {
            myChooseAccountDialog.show(fragmentManager!!, "ChooseAccountD")
        }
    }

    private fun openCreateAccountDialog() {
        activity?.let {
            mySignUpDialog.show(fragmentManager!!, "CreateAccountD")
        }
    }

    private fun openApprenderDialog() {
        activity?.let {
            myLoginDialog.show(fragmentManager!!, "login dialog")
        }

    }

//
//    override fun onLoginClick(id: Long, userName: String) {
//        Toast.makeText(context, userName, Toast.LENGTH_SHORT).show()
//        myLoginDialog.dismiss()
//        view?.let {view?.findNavController()!!.navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment(id)) }
//    }



}
