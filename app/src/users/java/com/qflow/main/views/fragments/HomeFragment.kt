package com.qflow.main.views.fragments

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
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.adapters.InfoRVAdapter
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.dialogs.JoinQueueDialog
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.users.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(),
    JoinQueueDialog.OnJoinDialogButtonClick,
    JoinQueueDialog.OnNavigateQRFragment,
    InfoQueueDialog.OnJoinClick {

    private val mViewModel: HomeViewModel by viewModel()

    private lateinit var currentQueues: InfoRVAdapter
    private lateinit var historyQueues: InfoRVAdapter

    private var mQueueDialog: InfoQueueDialog? = null
    private var mJoinQueueDialog: JoinQueueDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceSHometate: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeListeners()
        initializeRecyclers()
    }

    private fun initializeListeners() {
        initializeButtons()
    }

    private fun initializeButtons() {
        btn_join_queue.setOnClickListener {
            mJoinQueueDialog = JoinQueueDialog()
            mJoinQueueDialog!!.onAttachFragment(this)
            mJoinQueueDialog!!.show(this.childFragmentManager, "INFOQUEUEDIALOG")
        }
    }

    private fun initializeRecyclers() {
        currentQueues = InfoRVAdapter(ArrayList(), ::onClickQueues)
        rv_info_queues.adapter = currentQueues
        rv_info_queues.layoutManager = GridLayoutManager(
            context, 1, RecyclerView.VERTICAL,
            false
        )
        historyQueues = InfoRVAdapter(ArrayList(), ::onClickQueues)
        rv_history_queues.adapter = historyQueues
        rv_history_queues.layoutManager = GridLayoutManager(
            context, 1, RecyclerView.VERTICAL,
            false
        )

        //TODO We need the function LoadQueuesUserJoin (las que te has unio ya, vamos)
        mViewModel.getCurrentQueues("all", false)
        mViewModel.getHistoricalQueues("all", true)
    }

    private fun onClickQueues(queue: Queue) {
        mQueueDialog = InfoQueueDialog(queue, false)
        mQueueDialog!!.onAttachFragment(this)
        mQueueDialog!!.show(this.childFragmentManager, "JOINDIALOG")
    }


    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        when (renderState) {
            //Antes de mostrar la info de la cola a la que te quieres unir.
            is HomeFragmentScreenState.JoinedQueue -> {
                mQueueDialog?.dismiss()
                hideLoader()
                updateRV()
            }
            is HomeFragmentScreenState.QueueLoaded -> {
                hideLoader()
                mJoinQueueDialog?.dismiss()
                mQueueDialog = InfoQueueDialog(renderState.queue, true)
                mQueueDialog!!.onAttachFragment(this)
                mQueueDialog!!.show(this.childFragmentManager, "JOINDIALOG")
            }
            is HomeFragmentScreenState.QueuesActiveObtained -> {
                hideLoader()
                currentQueues.setData(renderState.queues)
            }
            is HomeFragmentScreenState.QueuesHistoricalObtained -> {
                historyQueues.setData(renderState.queues)
            }
        }

    }

    private fun updateRV() {
        mViewModel.getCurrentQueues("all", false)
    }
    private fun updateUI(screenState: ScreenState<HomeFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
                showLoader()
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun handleErrors(failure: Failure) {
        hideLoader()

        when (failure) {
            is Failure.NullResult -> {
                Toast.makeText(
                    this.context,
                    getString(R.string.QueueLoadingError),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun showLoader() {
        loading_bar_home.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loading_bar_home.visibility = View.INVISIBLE
    }

    private fun initializeObservers() {
        mViewModel.screenState.observe(::getLifecycle, ::updateUI)
        mViewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    //Cuando terminas de aceptar unirte a una cola.
    override fun handleJoinQueueRequest(queue: Queue) {
        mViewModel.joinToQueue(queue.joinId)
        onDetach()
    }

    override fun onNavigateQRFragment() {
        mJoinQueueDialog?.dismiss()
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_QRFragment)
    }

    override fun onJoinButtonClick(joinID: Int) {
        mViewModel.loadQueueToJoin(joinID)
    }
}
