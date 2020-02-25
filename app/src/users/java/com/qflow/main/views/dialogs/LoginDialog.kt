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

class LoginDialog(override val loginViewModel: LoginViewModel) : LoginDialogsInterface, DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View=inflater.inflate(R.layout.dialog_apprender, container, false)
        val cancelButton=rootView.findViewById<Button>(R.id.dialog_cancel)
        val submitButton=rootView.findViewById<Button>(R.id.dialog_submit)
        val textUsername=rootView.findViewById<EditText>(R.id.dialog_username)
        val textPassword=rootView.findViewById<EditText>(R.id.dialog_password)

        cancelButton.setOnClickListener { dismiss() }

        submitButton.setOnClickListener {
            val selectedUsername=textUsername.text.toString()
            val selectedPass=textPassword.text.toString()
//            loginViewModel.initiateNormalLogin(selectedUsername, selectedPass)
            dismiss()
        }
        return rootView
    }

}