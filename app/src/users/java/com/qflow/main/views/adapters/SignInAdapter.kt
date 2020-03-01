package com.qflow.main.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.domain.local.database.user.UserDB

/**
 * This class is used to give form to the recyclerViews
 * */

class SignInAdapter (val clickListener: SignInListener): ListAdapter<UserDB,
        RecyclerView.ViewHolder>(SignInDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

class SignInListener (val clickListener: (userDB: UserDB) -> Unit){
    fun onClick(userDB: UserDB) = clickListener(userDB)
}

class SignInDiffCallback: DiffUtil.ItemCallback<UserDB>(){
    override fun areItemsTheSame(oldItem: UserDB, newItem: UserDB): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserDB, newItem: UserDB): Boolean {
        return oldItem.username == newItem.username && oldItem.mail == newItem.mail && oldItem.password == newItem.password
    }


}