package com.qflow.main.views.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.qflow.main.R
import kotlinx.android.synthetic.users.dialog_join_queue.*


class JoinQueueDialog : DialogFragment(){

    interface OnJoinDialogButtonClick {
        fun onJoinButtonClick(joinID: Int)
    }

    interface OnNavigateQRFragment {
        fun onNavigateQRFragment()
    }

    private var mOnJoinButtonClick : OnJoinDialogButtonClick? = null
    private var mOnNavigateQRFragment : OnNavigateQRFragment? = null

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

        btn_scan_qr.setOnClickListener {
            mOnNavigateQRFragment?.onNavigateQRFragment()
        }
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        try {
            mOnNavigateQRFragment = context as OnNavigateQRFragment
            mOnJoinButtonClick = context as OnJoinDialogButtonClick
        } catch (e: ClassCastException){
            throw ClassCastException("$activity must implement OnJoinQR")
        }
    }

}
