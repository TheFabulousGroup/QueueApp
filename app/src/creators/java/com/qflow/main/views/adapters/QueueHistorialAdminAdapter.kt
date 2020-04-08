package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.creators.item_queueadmin.view.*
import kotlinx.android.synthetic.main.item_home_historical.view.*


class QueueHistorialAdminAdapter(
    private val queues: List<Queue>,
    private val onClickItemRV: (String) -> Unit
):RecyclerView.Adapter<QueueHistorialAdminAdapter.ViewHolder>() {


    override fun getItemCount(): Int = queues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_historical, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Se le esta pasando un Queue, hay que sacar el name de esa queue
        queues[position].name?.let { holder.bind(it) }
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(name: String) = with(itemView) {
            tv_queue_historical.text = name
            btn_view_historical.setOnClickListener {
                onClickItemRV(name)
            }
        }
    }


}