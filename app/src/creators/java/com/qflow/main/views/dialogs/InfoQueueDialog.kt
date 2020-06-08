package com.qflow.main.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.domain.local.models.Queue
import kotlinx.android.synthetic.creators.dialog_home_info_q.*
import net.glxn.qrgen.android.QRCode

class InfoQueueDialog(private val queue: Queue) : DialogFragment() {

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
        val myBitmap = QRCode.from(
            "{\"QflowQueue\": \""
                    + queue.joinId + "\"}"
        ).withSize(250, 250).bitmap()
        qrImageview?.setImageBitmap(myBitmap)
        home_info_queue_name.text = queue.name
        home_info_description_queue.text = queue.description
        home_info_bss_asoc_queue.text = queue.businessAssociated
        home_info_capacity_queue.text = queue.capacity.toString()
        home_info_dt_created.text = queue.dateCreated.toString()
        home_info_dt_finished_queue.text = queue.dateFinished.toString()
        home_info_is_active.text = queue.isLock.toString()
        home_info_join_id.text = queue.joinId.toString()
        home_info_num_persons.text = queue.numPersons.toString()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        try {

        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$activity " +
                        "must implement InfoQueueDialogFragment\n" +
                        e.message
            )
        }
    }
}