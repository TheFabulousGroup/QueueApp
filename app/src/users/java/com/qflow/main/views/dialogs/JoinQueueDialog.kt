package com.qflow.main.views.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.views.screenstates.JoinQueueScreenStates
import com.qflow.main.views.viewmodels.JoinQueueViewModel
import kotlinx.android.synthetic.users.dialog_join_queue.*
import org.koin.android.viewmodel.ext.android.viewModel


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
                loadingComplete()
                Toast.makeText(this.context, getString(R.string.QueueLoadingError), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(screenState: ScreenState<JoinQueueScreenStates>) {

        when (screenState) {
            ScreenState.Loading -> {
                loading()
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun renderScreenState(renderState: JoinQueueScreenStates) {
        loadingComplete()

        when (renderState) {
            is JoinQueueScreenStates.QueueLoaded -> {
                //TODO call to fragment function
                this.dismiss()
            }
        }

    }


    private fun loading(){
        loading_bar_dialog.visibility = View.VISIBLE
    }

    private fun loadingComplete(){
        loading_bar_dialog.visibility = View.INVISIBLE
    }
}
