package com.qflow.main.views.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.users.dialog_home_info_q.*


class InfoQueueDialog(
    private val queue: Queue,
    private val joinable: Boolean
) : DialogFragment() {

    interface OnJoinClick {
        fun handleJoinQueueRequest(queue: Queue)
    }

    private var mOnJoinClick: OnJoinClick? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_home_info_q, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQueueData()
    }

    private fun setQueueData() {
        home_info_queue_name.text = queue.name
        home_info_description_queue.text = queue.description
        home_info_bss_asoc_queue.text = queue.businessAssociated
        home_info_capacity_queue.text = queue.capacity.toString()
        home_info_dt_created.text = queue.dateCreated.toString()
        home_info_is_active.text = queue.isLock.toString()
        if (joinable)
            btnQueueJoinQueue.visibility = View.VISIBLE
        btnQueueJoinQueue.setOnClickListener {
            mOnJoinClick?.handleJoinQueueRequest(queue)
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)

        try {
            mOnJoinClick = childFragment as OnJoinClick
        } catch (e: ClassCastException){
            throw ClassCastException("$activity must implement OnJoinQR")
        }
    }
}