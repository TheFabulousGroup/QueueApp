package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.creators.item_queueadmin.view.*

class QueueAdminAdapter(
    private var queues: List<Queue>,
    private var onClickItemRV: (Queue) -> Unit
) : RecyclerView.Adapter<QueueAdminAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return queues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_queueadmin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Se le esta pasando un Queue, hay que sacar el name de esa queue
        queues[position].let { holder.onBind(it) }
    }

    fun setData(queuesUpdate: List<Queue>) {
        this.queues = queuesUpdate
        notifyDataSetChanged()
    }


    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun onBind(queue: Queue) = with(itemView) {
            tv_queue.text = queue.name
            tv_num_persons.text = "persons:" + queue.numPersons.toString()
            btn_view.setOnClickListener {
                onClickItemRV(queue)
            }
            btn_advanced.setOnClickListener {
                onClickItemRV(queue)
            }
        }
    }


}