package com.queueapp.main.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.queueapp.main.database.user.User


@BindingAdapter("emailString")
fun TextView.emailString(item: User?) {
    item?.let {
        text = "Correo: " + item.mail
    }
}

@BindingAdapter("usernameString")
fun TextView.usernameString(item: User?) {
    item?.let {
        text = "Nombre de usuario " + item.username
    }
}
