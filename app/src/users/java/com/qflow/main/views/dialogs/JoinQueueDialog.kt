package com.qflow.main.views.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.JoinQueueScreenStates
import com.qflow.main.views.viewmodels.JoinQueueViewModel
import kotlinx.android.synthetic.users.dialog_join_queue.*
import kotlinx.android.synthetic.users.fragment_signup.*
import org.koin.android.architecture.ext.viewModel

class JoinQueueDialog : DialogFragment(){

    private val mViewModel: JoinQueueViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_join_queue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initializeObservers()
        this.initializeListeners()
    }

    private fun initializeListeners() {
        btn_join_queue_by_code.setOnClickListener{
            view.let {
                val join_code = dialog_join_code.text.toString()
                mViewModel.joinQueue(join_code)
            }
        }
    }

    private fun initializeObservers() {
        mViewModel.screenState.observe(this.viewLifecycleOwner, Observer {
            updateUI(it)
        })
        mViewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    @SuppressLint("ResourceAsColor")
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.JoinNotSuccessful -> {
                Toast.makeText(this.context, "Join was not successful", Toast.LENGTH_SHORT).show()
            }
            is Failure.NullResult -> {
                view?.findNavController()?.navigate(R.id.action_joinQueueDialog_to_homeFragment)
            }
        }
    }

    private fun updateUI(screenState: ScreenState<JoinQueueScreenStates>) {

        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: JoinQueueScreenStates) {

        when (renderState) {
            is JoinQueueScreenStates.JoinSuccessful -> {
                //Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
                view?.findNavController()?.navigate(R.id.action_joinQueueDialog_to_homeFragment)
            }
        }

    }

    private fun loading(){
        //Make sure you've added the loader to the view
        loading_bar.visibility = View.VISIBLE
    }

    private fun loadingComplete(){
        loading_bar.visibility = View.INVISIBLE
    }
}
