package com.qflow.main.views.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R


class ProfileAdapter:RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    var data = listOf<Profile>()//¿Queue?

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = data[position]
       holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val textQ:TextView=itemView.findViewById(R.id.queue)
        val textH:TextView = itemView.findViewById(R.id.historical)
        fun bind(item:Queue){
            val res = itemView.context.resources
            textQ.text//= los valores de la  cola en
            textH.text //= los valores de la cola historico
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_profile, parent, false)
                return ViewHolder(view)
            }
        }

    }
}