package com.qflow.main.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.qflow.main.R
import com.qflow.main.domain.local.database.user.UserDB


//@SuppressLint("SetTextI18n")
//@BindingAdapter("emailString")
//fun TextView.emailString(item: UserDB?) {
//    val mail = context.getString(R.string.mail)
//    item?.let {
//        text = mail + item.
//    }
//}

@SuppressLint("SetTextI18n")
@BindingAdapter("usernameString")
fun TextView.usernameString(item: UserDB?) {
    val username = "UserName "
    item?.let {
        text = username + item.username
    }
}
