package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.generated.callback.OnClickListener
import kotlinx.android.synthetic.creators.item_queueadmin.view.*

//Todo cambiar el String por objeto
class QueueAdminAdapter(
    private val queues: List<Queue>,
    private val onClickItemRV: (String) -> Unit
):RecyclerView.Adapter<QueueAdminAdapter.ViewHolder>() {


    override fun getItemCount(): Int = queues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_queueadmin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Se le esta pasando un Queue, hay que sacar el name de esa queue
        holder.bind(queues[position])
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(name: String) = with(itemView) {
            tv_queue.text = name
            btn_add.setOnClickListener {
                onClickItemRV(name)
            }
        }
    }


}