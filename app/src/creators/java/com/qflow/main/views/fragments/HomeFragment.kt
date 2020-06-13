package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qflow.main.R
import com.qflow.main.R.layout
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.adapters.QueueAdminAdapter
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.dialogs.ManagementQueueDialog
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.creators.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

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
    }

    private fun initializeButtons() {

        btn_create.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_createQueueFragment)
        }

    }

    private fun initializeRecycler() {

        queuesAdminHistory = QueueAdminAdapter(ArrayList(), ::onClickOnQueue)
        queuesAdminAdapter = QueueAdminAdapter(ArrayList(), ::onClickOnQueue)

        rv_adminqueues.adapter = queuesAdminAdapter
        rv_adminhistorial.adapter = queuesAdminHistory
        rv_adminhistorial.layoutManager =
            GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        rv_adminqueues.layoutManager =
            GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        viewModel.getQueues("ALL", false) //alluser, null All queues from that user
        viewModel.getHistory("ALL", true) //history, null All queues with a dateFinished
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
            is HomeFragmentScreenState.QueueInfoDialog -> {
                mInfoQueueDialog = InfoQueueDialog(renderState.queues)
                mInfoQueueDialog!!.onAttachFragment(this)
                mInfoQueueDialog!!.show(this.childFragmentManager, "INFODIALOG")
            }
            is HomeFragmentScreenState.QueueManageDialog -> {
                mManageQueueDialog = ManagementQueueDialog(renderState.queues)
                mManageQueueDialog!!.onAttachFragment(this)
                mManageQueueDialog!!.show(this.childFragmentManager, "MANAGEMENTDIALOG")
            }
        }
    }

    private fun onClickOnQueue(queue: Queue) {
        mInfoQueueDialog = InfoQueueDialog(queue)
        mInfoQueueDialog!!.onAttachFragment(this)
        mInfoQueueDialog!!.show(this.childFragmentManager, "INFODIALOG")

        /* mManageQueueDialog = ManagementQueueDialog(queue)
         mManageQueueDialog!!.onAttachFragment(this)
         mManageQueueDialog!!.show(this.childFragmentManager, "MANAGEMENTDIALOG")*/

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

    //TODO
    /*
    override fun onStopButtonClick(queue: Queue) {
        queue.id?.let { viewModel.stopQueue(it) }
    }
    override  fun onCloseButtonClick(queue:Queue){
         queue.id?.let {viewModel.closeQueue(it)}
     }

     override fun onAdvanceButtonClick(queue:Queue){
         //queue.id?.let {viewModel.closeQueue(it)}
     }

     override fun onResumeButtonClick(queue:Queue){
         queue.id?.let {viewModel.resumeQueue(it)}
     }*/
}


