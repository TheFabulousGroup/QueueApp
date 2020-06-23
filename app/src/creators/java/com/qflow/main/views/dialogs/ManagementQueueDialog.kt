package com.qflow.main.views.dialogs

import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_manage_queue.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue


class ManagementQueueDialog(
    private val queue: Queue
) : DialogFragment() {

    interface OnStopDialogButtonClick {
        fun onStopButtonClick(queue: Queue)
    }

    interface OnCloseDialogButtonClick {
        fun onCloseButtonClick(queue: Queue)
    }

    interface OnResumeDialogButtonClick {
        fun onResumeButtonClick(queue: Queue)
    }

    interface OnAdvanceDialogButtonClick {
        fun onAdvanceButtonClick(queue: Queue)
    }

    private var mOnStopDialogButtonClick: OnStopDialogButtonClick? = null
    private var mOnCloseDialogButtonClick: OnCloseDialogButtonClick? = null
    private var mOnResumeDialogButtonClick: OnResumeDialogButtonClick? = null
    private var mOnAdvanceDialogButtonClick: OnAdvanceDialogButtonClick? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_manage_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListener()
    }

    private fun initializeListener() {
        btn_close.setOnClickListener {
            loading_bar_manage.visibility = View.VISIBLE
            mOnCloseDialogButtonClick?.onCloseButtonClick(queue)
        }

        btn_advance.setOnClickListener {
            loading_bar_manage.visibility = View.VISIBLE
            mOnAdvanceDialogButtonClick?.onAdvanceButtonClick(queue)
        }

        btn_stop.setOnClickListener {
            loading_bar_manage.visibility = View.VISIBLE
            mOnStopDialogButtonClick?.onStopButtonClick(queue)
        }

        btn_resume.setOnClickListener {
            loading_bar_manage.visibility = View.VISIBLE
            mOnResumeDialogButtonClick?.onResumeButtonClick(queue)
        }
        setQueueData()
        checkQueues()
    }

    private fun checkQueues() {
        if (queue.dateFinished != null) {
            btn_close.visibility = VISIBLE
        }
        if (queue.lock!!) {
            btn_resume.visibility = VISIBLE
            btn_stop.visibility = INVISIBLE
            btn_advance.visibility = INVISIBLE
        } else {
            btn_resume.visibility = INVISIBLE
            btn_stop.visibility = VISIBLE
            btn_advance.visibility = VISIBLE
        }
    }

    private fun setQueueData() {
        //TODO add correct text
        tv_advance.text = "Next person: " + queue.nextPerson
        tv_queue_name_d.text = queue.name

    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        try {
            mOnAdvanceDialogButtonClick = childFragment as OnAdvanceDialogButtonClick
            mOnResumeDialogButtonClick = childFragment as OnResumeDialogButtonClick
            mOnStopDialogButtonClick = childFragment as OnStopDialogButtonClick
            mOnCloseDialogButtonClick = childFragment as OnCloseDialogButtonClick
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity " +
                        "must implement ManagementQueueDialog\n" +
                        e.message
            )
        }
    }
}