package com.qflow.main.views.fragments

import android.app.SharedElementCallback
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.R.layout
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.activities.LoginActivity
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.dialogs.ManagementQueueDialog
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.creators.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), ManagementQueueDialog.OnAdvanceDialogButtonClick,
    ManagementQueueDialog.OnCloseDialogButtonClick, ManagementQueueDialog.OnResumeDialogButtonClick,
    ManagementQueueDialog.OnStopDialogButtonClick {

    private val viewModel: HomeViewModel by viewModel()
    private val sharedPrefsRepository: SharedPrefsRepository by inject()

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

        tv_user_name.text = sharedPrefsRepository.getUserName()
    }

    private fun initializeListeners() {
        initializeButtons()
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {
        btn_alogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this.context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
        btn_arefresh.setOnClickListener {
            updateRV()
        }
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
        viewModel.getQueues("creator", false) //alluser, null All queues from that user
        viewModel.getHistory("creator", true) //history, null All queues with a dateFinished
        loadingComplete()
    }


    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        when (renderState) {
            is HomeFragmentScreenState.QueuesActiveObtained -> {
                queuesAdminAdapter.setData(renderState.queues)
            }
            is HomeFragmentScreenState.QueuesHistoricalObtained -> {
                queuesAdminHistory.setData(renderState.queues)
                loadingComplete()
            }
            is HomeFragmentScreenState.QueueInfoDialog -> {
                mInfoQueueDialog = InfoQueueDialog(renderState.queues, false)
                mInfoQueueDialog!!.onAttachFragment(this)
                mInfoQueueDialog!!.show(this.childFragmentManager, "INFODIALOG")
            }
            is HomeFragmentScreenState.QueueManageDialog -> {
                mManageQueueDialog?.dialog?.let {
                    if (it.isShowing)
                        mManageQueueDialog!!.dismiss()
                }
                initializeDialogManagement(renderState.queues)
                loadingComplete()
                updateRV()
            }
            is HomeFragmentScreenState.QueueClosed -> {
                mManageQueueDialog?.dismiss()
                updateRV()
            }
        }
    }

    private fun initializeDialogManagement(queue: Queue) {
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
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.FULL_CAPACITY -> {
                        Toast.makeText(
                            this.context, "Queue is full", Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_CLOSE -> {
                        Toast.makeText(
                            this.context,
                            "You can´t advance a queue that has been closed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_LOCK -> {
                        Toast.makeText(
                            this.context,
                            "If you want advance the queue that is stopped, first resume queue",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_STOP -> {
                        Toast.makeText(
                            this.context,
                            "The queue is already stopped",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_RESUME -> {
                        Toast.makeText(
                            this.context,
                            "The queue is already on",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_ADVANCE_CLOSE -> {
                        Toast.makeText(
                            this.context,
                            "You can´t advance a queue which has been closed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ValidationFailureType.QUEUE_CLOSE_CLOSED -> {
                        Toast.makeText(
                            this.context,
                            "You can´t close a queue which has been closed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            is Failure.ServerException -> {
                loadingComplete()
                Toast.makeText(
                    this.context,
                    getString(R.string.LoadQueue),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Failure.QueuesNotFound -> {
                loadingComplete()
                Toast.makeText(
                    this.context,
                    getString(R.string.queues_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Failure.CantAdvanceQueue -> {
                loadingComplete()
                mManageQueueDialog?.dialog?.let {
                    if (it.isShowing)
                        mManageQueueDialog!!.dismiss()
                }
                Toast.makeText(
                    this.context,
                    getString(R.string.queues_not_advance),
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

    private fun updateRV() {
        loading()

        viewModel.getQueues("creator", false) //alluser, null All queues from that user
        viewModel.getHistory("creator", true) //history, null All queues with a dateFinished
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
        queue.id?.let {
            queue.lock?.let { it1 -> viewModel.stopQueue(it, it1) }
            Toast.makeText(
                this.context,
                "Queue " + queue.name + " has been stopped",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCloseButtonClick(queue: Queue) {
        queue.id?.let {
            viewModel.closeQueue(it)
        }
        Toast.makeText(
            this.context,
            "Queue  " + queue.name + "has been closed",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAdvanceButtonClick(queue: Queue) {
        queue.id?.let {
            viewModel.advanceQueue(queue.id)
        }
        Toast.makeText(
            this.context,
            "Queue" + queue.name + "has been advanced",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResumeButtonClick(queue: Queue) {
        queue.id?.let {
            queue.lock?.let { it1 -> viewModel.resumeQueue(it, it1) }
            Toast.makeText(
                this.context,
                "Queue" + queue.name + "has been resumed",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

}


