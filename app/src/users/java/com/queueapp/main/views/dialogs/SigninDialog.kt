package com.queueapp.main.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.queueapp.main.R
import com.queueapp.main.views.viewmodels.LoginViewModel

class SigninDialog(override val loginViewModel: LoginViewModel) : LoginDialogsInterface, DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =inflater.inflate(R.layout.dialog_signin, container, false)
        val cancelButton=rootView.findViewById<Button>(R.id.dialog_cancel)
        val submitButton=rootView.findViewById<Button>(R.id.dialog_submit)
        val textUsername=rootView.findViewById<EditText>(R.id.dialog_username)
        val textPassword=rootView.findViewById<EditText>(R.id.dialog_password)

        cancelButton.setOnClickListener { dismiss() }

        //TODO [RecyclerView]show recycler view



        return rootView
    }

}