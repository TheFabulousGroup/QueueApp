package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.viewmodels.CreateQueueViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.findNavController
import com.qflow.main.core.Failure
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.CreateQueueScreenState
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import kotlinx.android.synthetic.creators.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_create_queue.*


class CreateQueueFragment : Fragment() {

    private val viewModel: CreateQueueViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeListeners()
        initializeObservers()
    }

    private fun initializeListeners() {
        accept_create_queue.setOnClickListener {
            val nameCreateQueue = name_create_queue.text.toString()
            val businessAssociated = business_associated.text.toString()
            val queueDescription = queue_description_create_queue.text.toString()
            val capacity = capacity.text.toString()
            viewModel.createQueueInDatabase(
                nameCreateQueue, businessAssociated, queueDescription, capacity
            )
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
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

    private fun updateUI(screenState: ScreenState<CreateQueueScreenState>?) {

        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: CreateQueueScreenState) {

        when (renderState) {
            is CreateQueueScreenState.QueueCreatedCorrectly -> {
                //Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
                view?.let {
                    view?.findNavController()!!
                        .navigate(
                            LoginFragmentDirections //TODO cambiar a CreateQUeueDirections (navigation)
                                .actionLoginFragmentToProfileFragment(renderState.id)
                        )
                }
            }
        }

    }

}