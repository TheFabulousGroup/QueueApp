package com.qflow.main.views.dialogs

import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_manage_queue.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue


class ManagementQueueDialog(
    private val queue: Queue
) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_manage_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setQueueData()
    }

    private fun setQueueData() {

        tv_advanced.text = queue.currentPos.toString()

    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        try {

        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity " +
                        "must implement ManagementQueueDialog\n" +
                        e.message
            )
        }
    }
}