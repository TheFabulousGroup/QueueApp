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
    private val queue: Queue,
    private val flag: Boolean //TODO give value in hOmeFragment
) : DialogFragment() {

    interface OnStopDialogButtonClick {
        fun onStopButtonClick(queueId: Int)
    }

    interface OnCloseDialogButtonClick {
        fun onCloseButtonClick(queueId: Int)
    }

    interface OnResumeDialogButtonClick {
        fun onResumeButtonClick(queueId: Int)
    }

    interface OnAdvanceDialogButtonClick {
        fun onAdvanceButtonClick(queueId: Int)
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
        setQueueData()
        initializeListener()
    }

    private fun initializeListener() {
        btn_close.setOnClickListener {
            mOnCloseDialogButtonClick?.onCloseButtonClick(1)
        }

        btn_plus.setOnClickListener {
            mOnAdvanceDialogButtonClick?.onAdvanceButtonClick(1)
        }

        btn_stop_resume.setOnClickListener {
            if (flag) {
                mOnStopDialogButtonClick?.onStopButtonClick(1)
            } else {
                mOnResumeDialogButtonClick?.onResumeButtonClick(1)
            }
        }
    }

    private fun setQueueData() {

        tv_advanced.text = queue.currentPos.toString()

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