package com.qflow.main.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.qflow.main.R


import com.qflow.main.databinding.ActivityLoginBinding
import com.qflow.main.repository.UserRepository
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        //auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
    }


}
