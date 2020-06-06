package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.users.item_home.view.*


class InfoRVAdapter(
    private var queues: List<Queue>,
    private var onClickItemRV: (Queue) -> Unit
) : RecyclerView.Adapter<InfoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoRVAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return queues.size
    }

    override fun onBindViewHolder(holder: InfoRVAdapter.ViewHolder, position: Int) {
        queues[position].let { holder.onBind(it) }
    }

    fun setData(queuesUpdate: List<Queue>) {
        this.queues = queuesUpdate
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(queue: Queue) {
            itemView.tv_user_queue.text = queue.name
            if (queue.inFrontOfUser == 0) {
                itemView.tv_front_user.text = "Your turn"
            } else {
                itemView.tv_front_user.text = "Persons in front: " + queue.inFrontOfUser.toString()
            }
            itemView.btn_u_view.setOnClickListener {
                onClickItemRV(queue)
            }
        }
    }

}