package com.qflow.main.views.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.domain.local.database.user.User

class SignInAdapter (val clickListener: SignInListener): ListAdapter<User,
        RecyclerView.ViewHolder>(SignInDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
//    ListAdapter<DataItem,
//        RecyclerView.ViewHolder>(SleepNightDiffCallback())

class SignInListener (val clickListener: (user: User) -> Unit){
    fun onClick(user: User) = clickListener(user)
}

class SignInDiffCallback: DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.username == newItem.username && oldItem.mail == newItem.mail && oldItem.password == newItem.password
    }


}