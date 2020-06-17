package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.R.layout
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.dialogs.ManagementQueueDialog
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.creators.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), ManagementQueueDialog.OnAdvanceDialogButtonClick,
    ManagementQueueDialog.OnCloseDialogButtonClick, ManagementQueueDialog.OnResumeDialogButtonClick,
    ManagementQueueDialog.OnStopDialogButtonClick {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var queuesAdminAdapter: QueueAdminAdapter
    private lateinit var queuesAdminHistory: QueueAdminAdapter
    private var mInfoQueueDialog: InfoQueueDialog? = null
    private var mManageQueueDialog: ManagementQueueDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeListeners()
        initializeRecycler()
    }

    private fun initializeListeners() {
        initializeButtons()
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {

        btn_create.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_createQueueFragment)
        }

    }

    private fun initializeRecycler() {

        queuesAdminAdapter =
            QueueAdminAdapter(ArrayList(), ::onClickOnQueue, ::onClickManageQueue, false)
        queuesAdminHistory =
            QueueAdminAdapter(ArrayList(), ::onClickHistoricalOnQueue, null, true)

        rv_adminqueues.adapter = queuesAdminAdapter
        rv_adminhistorial.adapter = queuesAdminHistory
        rv_adminhistorial.layoutManager =
            GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        rv_adminqueues.layoutManager =
            GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        viewModel.getQueues("all", false) //alluser, null All queues from that user
        viewModel.getHistory("all", true) //history, null All queues with a dateFinished
    }


    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        loadingComplete()
        when (renderState) {
            is HomeFragmentScreenState.QueuesActiveObtained -> {
                queuesAdminAdapter.setData(renderState.queues)
            }
            is HomeFragmentScreenState.QueuesHistoricalObtained -> {
                queuesAdminHistory.setData(renderState.queues)
            }
            is HomeFragmentScreenState.QueueManageDialog -> {
                initializeDialogManagement(renderState.queues)
            }
        }
    }

    private fun initializeDialogManagement(queue: Queue) {
        if(mManageQueueDialog != null)
            mManageQueueDialog!!.dismiss()
        mManageQueueDialog = ManagementQueueDialog(queue)
        mManageQueueDialog!!.onAttachFragment(this)
        mManageQueueDialog!!.show(this.childFragmentManager, "MANAGEMENTDIALOG")
    }

    private fun onClickOnQueue(queue: Queue) {
        mInfoQueueDialog = InfoQueueDialog(queue, false)
        mInfoQueueDialog!!.onAttachFragment(this)
        mInfoQueueDialog!!.show(this.childFragmentManager, "INFODIALOG")
    }

    private fun onClickHistoricalOnQueue(queue: Queue) {
        mInfoQueueDialog = InfoQueueDialog(queue, true)
        mInfoQueueDialog!!.onAttachFragment(this)
        mInfoQueueDialog!!.show(this.childFragmentManager, "INFODIALOG")
    }

    private fun onClickManageQueue(queue: Queue) {
        mManageQueueDialog = ManagementQueueDialog(queue)
        mManageQueueDialog!!.onAttachFragment(this)
        mManageQueueDialog!!.show(this.childFragmentManager, "MANAGEMENTDIALOG")
    }

    private fun handleErrors(failure: Failure?) {
        /*is Failure.ValidationFailure -> {
            //Remember to stop loading when you need to
            loadingComplete()
            when (failure.validationFailureType) {
                ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY -> {
                    Toast.makeText(
                        this.context, "Email or password empty", Toast.LENGTH_LONG
                    ).show()
                    this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }
                        ?.let {
                            inputPass.background.setTint(it)
                            inputEmail.background.setTint(it)
                        }
                }
            }*/
    }

    private fun updateUI(screenState: ScreenState<HomeFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
                loading()
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }


    private fun initializeObservers() {
        viewModel.screenState.observe(::getLifecycle, ::updateUI)
    }

    private fun loading() {

        loading_bar_home.visibility = View.VISIBLE
    }

    private fun loadingComplete() {
        loading_bar_home.visibility = View.INVISIBLE
    }

    override fun onStopButtonClick(queue: Queue) {
        queue.id?.let { viewModel.stopQueue(it) }
    }

    override fun onCloseButtonClick(queue: Queue) {
        queue.id?.let { viewModel.closeQueue(it) }
    }

    override fun onAdvanceButtonClick(queue: Queue) {
        queue.id?.let { viewModel.advanceQueue(it) }
    }

    override fun onResumeButtonClick(queue: Queue) {
        queue.id?.let { viewModel.resumeQueue(it) }
    }
}


