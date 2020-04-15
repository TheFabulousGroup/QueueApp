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
import com.qflow.main.core.ScreenState
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.screenstates.InfoQueueScreenState
import com.qflow.main.views.viewmodels.CreateQueueViewModel
import com.qflow.main.views.viewmodels.HomeViewModel
import com.qflow.main.views.viewmodels.InfoQueueViewModel
import kotlinx.android.synthetic.creators.item_queueadmin.*
import kotlinx.android.synthetic.main.dialog_home_info_q.*
import kotlinx.android.synthetic.main.dialog_home_info_q.view.*
import kotlinx.android.synthetic.main.dialog_home_info_q.view.home_info_capacity_queue
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoQueueDialog : DialogFragment() {

    private val viewModel: InfoQueueViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val idQueue = arguments?.getString("idQueue")
        if (idQueue != null) {
            viewModel.fetchQueueById(idQueue)
        }

        return inflater.inflate(R.layout.dialog_home_info_q, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObservers()
        initializeListeners()
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        //Failure with HandleErrors?
    }

    private fun initializeListeners() {
        btn_edit_queue.setOnClickListener{
            //TODO EditQueue CU
        }
    }

    private fun updateUI(screenState: ScreenState<InfoQueueScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: InfoQueueScreenState) {
        when (renderState) {
            is InfoQueueScreenState.QueueObtained -> {
                home_info_queue_name.text = renderState.queue.name
                home_info_description_queue.text = renderState.queue.description
                home_info_bss_asoc_queue.text = renderState.queue.business_associated
                home_info_capacity_queue.text = renderState.queue.capacity.toString()
                home_info_dt_created.text = renderState.queue.date_created.toString()
                home_info_dt_finished_queue.text = renderState.queue.date_finished.toString()
                home_info_is_active.text = renderState.queue.is_active.toString()
                home_info_join_id.text = "Join ID (add)"
            }
        }
    }

}