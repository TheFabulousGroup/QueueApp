package com.qflow.main.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.qflow.main.R
import com.qflow.main.views.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginDialog(override val loginViewModel: LoginViewModel) : LoginDialogsInterface, DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View=inflater.inflate(R.layout.activity_login, container, false)
        val textUsername=rootView.findViewById<EditText>(R.id.inputEmail)
        val textPassword=rootView.findViewById<EditText>(R.id.inputPass)
        val submitButton=rootView.findViewById<Button>(R.id.accept_Login)
        val registerButton=rootView.findViewById<Button>(R.id.btn_signUp)


        submitButton.setOnClickListener {
            val selectedUsername=textUsername.text.toString()
            val selectedPass=textPassword.text.toString()
            dismiss()
        }
        registerButton.setOnClickListener{
            val register = btn_signUp
        }
        return rootView
    }

}