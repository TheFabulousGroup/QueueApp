package com.qflow.main.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.InfoQueueScreenState
import com.qflow.main.views.viewmodels.InfoQueueViewModel
import kotlinx.android.synthetic.creators.dialog_home_info_q.*
import net.glxn.qrgen.android.QRCode
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoQueueDialog : DialogFragment() {

    private val viewModel: InfoQueueViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val idQueue = arguments?.getInt("idQueue")
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
                val myBitmap = QRCode.from("\"QflowQueue\": \""
                        + renderState.queue.joinId + "\"").withSize(250,250).bitmap()
                qrImageview?.setImageBitmap(myBitmap)
                home_info_queue_name.text = renderState.queue.name
                home_info_description_queue.text = renderState.queue.description
                home_info_bss_asoc_queue.text = renderState.queue.businessAssociated
                home_info_capacity_queue.text = renderState.queue.capacity.toString()
                home_info_dt_created.text = renderState.queue.dateCreated.toString()
                home_info_dt_finished_queue.text = renderState.queue.dateFinished.toString()
                home_info_is_active.text = renderState.queue.isLock.toString()
                home_info_join_id.text = "Join ID (add)"
            }
        }
    }

}