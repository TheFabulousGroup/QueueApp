package com.qflow.main.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.qflow.main.R
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.creators.item_queueadmin.*
import kotlinx.android.synthetic.main.dialog_home_info_q.view.*

class InfoQueueDialog(override val homeViewModel: HomeViewModel) : HomeQueueInfoInterface, DialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView: View = inflater.inflate(R.layout.dialog_home_info_q, container, false)
        val textNameQueue = rootView.findViewById<TextView>(R.id.home_info_queue_name)
        val textDescriptionQueue = rootView.findViewById<TextView>(R.id.home_info_bss_asoc_queue)
        val textBusiness = rootView.findViewById<TextView>(R.id.home_info_description_queue)
        val textCapacityQueue = rootView.findViewById<TextView>(R.id.home_info_capacity_queue)
        val textDateCreatedQueue = rootView.findViewById<TextView>(R.id.home_info_dt_created)
        val textDateFinishededQueue = rootView.findViewById<TextView>(R.id.home_info_dt_finished_queue)
        val textIsActiveQueue = rootView.findViewById<TextView>(R.id.home_info_is_active)
        val textJoin = rootView.findViewById<TextView>(R.id.home_info_join_id)
        val btn_edit = rootView.findViewById<Button>(R.id.btn_edit_queue)




        btn_edit.setOnClickListener {

        }
        /* val textUsername=rootView.findViewById<EditText>(R.id.inputEmail)
         val textPassword=rootView.findViewById<EditText>(R.id.inputPass)
         val submitButton=rootView.findViewById<Button>(R.id.btn_signUp)
         val registerButton=rootView.findViewById<Button>(R.id.btn_signIn)*/

/*
        submitButton.setOnClickListener {
            val selectedemail=textUsername.text.toString()
            val selectedPass=textPassword.text.toString()

        }*/

        return rootView
    }
}