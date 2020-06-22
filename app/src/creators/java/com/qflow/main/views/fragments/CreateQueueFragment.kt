package com.qflow.main.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.ScreenState
import com.qflow.main.views.viewmodels.CreateQueueViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.qflow.main.core.Failure
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.android.synthetic.creators.fragment_login.*
import kotlinx.android.synthetic.main.fragment_create_queue.*


class CreateQueueFragment : Fragment() {

    private val mViewModel: CreateQueueViewModel by viewModel()

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
            val cap = Integer.parseInt(capacity.text.toString())
            val avgServiceTime = Integer.parseInt(avg_service_time.text.toString())
            mViewModel.createQueueInDatabase(
                nameCreateQueue, queueDescription, cap, businessAssociated, avgServiceTime
            )
        }

    }

    private fun initializeObservers() {
        mViewModel.screenState.observe(::getLifecycle, ::updateUI)
        mViewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    @SuppressLint("ResourceAsColor")
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                loadingComplete()
                when (failure.validationFailureType) {
                    ValidationFailureType.CAPACITY_TOO_SMALL -> {
                        Toast.makeText(
                            this.context,
                            "Capacity time must be greater than 0",
                            Toast.LENGTH_LONG
                        ).show()
                        this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }
                            ?.let {
                                capacity.background.setTint(it)
                            }

                    }
                    ValidationFailureType.FIELDS_EMPTY -> {
                        Toast.makeText(
                            this.context, "The field are empty", Toast.LENGTH_LONG
                        ).show()
                        this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }
                            ?.let {
                                name_create_queue.background.setTint(it)
                                business_associated.background.setTint(it)
                                queue_description_create_queue.background.setTint(it)
                                capacity.background.setTint(it)
                                avg_service_time.background.setTint(it)
                            }
                        }

                ValidationFailureType.AVG_TIME -> {
                    Toast.makeText(
                        this.context,
                        "Average time must be greater than 0",
                        Toast.LENGTH_LONG
                    ).show()
                    this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }
                        ?.let {
                            avg_service_time.background.setTint(it)
                        }
                    }
                }
            }
            else->{
                loadingComplete()
                Toast.makeText(
                    this.context,
                    getString(R.string.create_queue_not_successful),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun updateUI(screenState: ScreenState<HomeFragmentScreenState>?) {

        when (screenState) {
            ScreenState.Loading -> {
                loading()
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        loadingComplete()
        when (renderState) {
            is HomeFragmentScreenState.QueueCreatedCorrectly -> {
                Toast.makeText(this.context, renderState.id, Toast.LENGTH_LONG).show()
                view?.findNavController()?.navigate(R.id.action_createQueueFragment_to_homeFragment)
            }
        }

    }

    private fun loading() {
        //Make sure you've added the loader to the view
        loading_bar_create_queue.visibility = View.VISIBLE
    }

    private fun loadingComplete() {
        loading_bar_create_queue.visibility = View.INVISIBLE
    }
}