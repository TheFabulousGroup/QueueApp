package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.qflow.main.R.*
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.activities.HomeActivityDirections
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.creators.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var queuesAdminAdapter: QueueAdminAdapter
    fun activateRecyclerView(/**/) {
        //if(R.id.btn_add == view.id){

    }

    // val adapter:CurrentInfoAdapter(/*QueueAdapter*/)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeListeners()
        viewModel.getQueues("Gl3rWCDvZFv9sm1LveHm")
    }

    private fun initializeListeners() {
        initializeButtons()
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {
        //img_profile.setImageResource()
        btn_create.setOnClickListener {
            view?.let {
                view?.findNavController()!!
                    .navigate(HomeActivityDirections.actionProfileFragmentToCreateQueueFragment())
            }
        }
    }

    private fun initializeRecycler() {

    }

    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY -> {//Cambiar por profile
                        //TODO añadir aqui que hacer cuando el validador de fallo

                    }
                }
            }
        }
    }

    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        when (renderState) {
            is HomeFragmentScreenState.AccessHome -> {
                view?.let {
                    view?.findNavController()!!
                    /*.navigate(
                   ¿Siguiente vista?
                )
                }*/
                }
            }
            //TODO
            is HomeFragmentScreenState.QueuesObtained -> {
                queuesAdminAdapter = QueueAdminAdapter(renderState.queues, ::onClickOnQueue)
                rv_adminqueues.adapter = queuesAdminAdapter
            }
            //TODO Historical
        }
    }

    private fun onClickOnQueue(s: String) {
        //TODO navigate to queue view
    }

    private fun updateUI(screenState: ScreenState<HomeFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            updateUI(it)
        })
    }

}


