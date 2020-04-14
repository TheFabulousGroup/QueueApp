package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.viewmodels.CreateQueueViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.findNavController
import com.qflow.main.core.Failure
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.CreateQueueScreenState
import com.qflow.main.views.screenstates.EditQueueScreenState
import kotlinx.android.synthetic.main.fragment_create_queue.*
import kotlinx.android.synthetic.main.fragment_edit_queue.*


class EditQueueFragment : Fragment() {
    //TODO: change to editQueueViewModel
    private val viewModel: CreateQueueViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val idQueue = arguments?.getString("id_queue_edit")
        id_queue_edit.text = idQueue

        return inflater.inflate(R.layout.fragment_edit_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializeListeners()
        //initializeObservers()
        initializeButtons()

    }

    /*private fun initializeListeners() {
            initializeButtons()

    }*/

    /*
    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }*/

    private fun initializeButtons() {
        btn_accept_edit_queue.setOnClickListener {
                val nameCreateQueue = name_edit_queue.text.toString()
                val businessAssociated = business_associated_edit_queue.text.toString()
                val queueDescription = queue_description_edit_queue.text.toString()
                val capacity = capacity_edit_queue.text.toString()
                val dateCreated = date_created_edit_queue.text.toString()
                val dateFinished = date_finished_edit_queue.text.toString()
                val isActive = is_active_edit_queue.isChecked
            //TODO: create view model for EditQueue, pls
            /*
            viewModel.createQueueInDatabase(
                nameCreateQueue, businessAssociated, queueDescription, capacity
            )*/
        }
    }
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.CAPACITY_TOO_SMALL -> {
                        //TODO añadir aqui que hacer cuando el validador de fallo

                    }
                }
            }
        }
    }

    private fun updateUI(screenState: ScreenState<EditQueueScreenState>?) {

        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: EditQueueScreenState) {

        when (renderState) {
            is EditQueueScreenState.QueueEditedCorrectly -> {
                //Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
                //view?.findNavController()?.navigate(R.id.action_createQueueFragment_to_homeFragment)
            }
        }

    }

}