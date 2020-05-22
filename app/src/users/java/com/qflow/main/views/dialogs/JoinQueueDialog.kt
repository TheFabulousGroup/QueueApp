package com.qflow.main.views.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.users.dialog_join_queue.*
import org.koin.android.viewmodel.ext.android.viewModel


class JoinQueueDialog : DialogFragment(){

    interface OnJoinButtonClick {
        fun onJoinButtonClick(joinID: Int)
    }

    private var mOnJoinButtonClick : OnJoinButtonClick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_join_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListeners()
    }

    private fun initializeListeners() {
        btn_join_queue_by_code.setOnClickListener{
            view.let {
                val joinCode = dialog_join_code.text.toString()
                mOnJoinButtonClick?.onJoinButtonClick(joinCode.toInt())
            }
        }
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        try {
            mOnJoinButtonClick = context as OnJoinButtonClick
        } catch (e: ClassCastException){
            throw ClassCastException("$activity must implement OnJoinQR")
        }
    }

}
