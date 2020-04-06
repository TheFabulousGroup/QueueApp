package com.qflow.main.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.generated.callback.OnClickListener
//Todo cambiar el String por objeto
class CurrentInfoAdapter(private val click:(List<String>)->Unit): RecyclerView.Adapter<CurrentInfoAdapter.ViewHolder>() {
    private var data = listOf(QueueAdapter)
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_homecurrent, parent, false)
        return ViewHolder(view)
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item,click)
    }


    inner class ViewHolder(item:View):RecyclerView.ViewHolder(item){

        val textname = item.findViewById<TextView>(R.id.textViewQ)

        fun bind(item:String , clickListener: OnClickListener) {

            textname = 

            btn_add.setOnClickListener{
                viewModel.saveData()
            }

        }

    }

}