package com.qflow.main.views.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import com.qflow.main.views.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Old view used for the login (pending to be deleted)
 * */

class LoginFragment : Fragment() {

    private lateinit var myLoginDialog: LoginDialog
    private lateinit var myChooseAccountDialog: SigninDialog
    private lateinit var mySignUpDialog: SignupDialog

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            //Añadir aqui estados de la aplicacion loginScreenState
        }

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
