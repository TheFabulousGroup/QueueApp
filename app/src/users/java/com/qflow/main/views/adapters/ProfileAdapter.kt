package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.utils.TextViewHolder
import kotlinx.android.synthetic.main.profile_fragment.view.*

class ProfileAdapter:RecyclerView.Adapter<TextViewHolder>() {
    var data = listOf<TextViewHolder>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = data[position]
        holder.textView.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_profile, parent, false) as TextView
        return TextViewHolder(view)
    }

}