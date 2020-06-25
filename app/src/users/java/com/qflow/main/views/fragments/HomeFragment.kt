package com.qflow.main.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.activities.LoginActivity
import com.qflow.main.views.adapters.InfoRVAdapter
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.dialogs.JoinQueueDialog
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.users.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(),
    JoinQueueDialog.OnJoinDialogButtonClick,
    JoinQueueDialog.OnNavigateQRFragment,
    InfoQueueDialog.OnJoinClick {

    private val mViewModel: HomeViewModel by viewModel()
    private val sharedPrefsRepository: SharedPrefsRepository by inject()

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

        tv_user_name.text = sharedPrefsRepository.getUserName()
    }

    private fun initializeListeners() {
        initializeButtons()
    }

    private fun initializeButtons() {
        btn_refresh.setOnClickListener {
            updateRV()
        }
        btn_join_queue.setOnClickListener {
            mJoinQueueDialog = JoinQueueDialog()
            mJoinQueueDialog!!.onAttachFragment(this)
            mJoinQueueDialog!!.show(this.childFragmentManager, "INFOQUEUEDIALOG")
        }
        btn_logout.setOnClickListener {
            mViewModel.logout()
            val intent = Intent(this.context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }

    private fun initializeRecyclers() {
        currentQueues = InfoRVAdapter(ArrayList(), ::onClickQueues, true)
        rv_info_queues.adapter = currentQueues
        rv_info_queues.layoutManager = GridLayoutManager(
            context, 1, RecyclerView.VERTICAL,
            false
        )
        historyQueues = InfoRVAdapter(ArrayList(), ::onClickQueues, false)
        rv_history_queues.adapter = historyQueues
        rv_history_queues.layoutManager = GridLayoutManager(
            context, 1, RecyclerView.VERTICAL,
            false
        )

        //TODO We need the function LoadQueuesUserJoin (las que te has unio ya, vamos)
        mViewModel.getCurrentQueues("user", false)
        mViewModel.getHistoricalQueues("user", true)
    }

    private fun onClickQueues(queue: Queue, isActive: Boolean) {
        mQueueDialog = InfoQueueDialog(queue, false, isActive)
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
            is HomeFragmentScreenState.QueueToJoinLoaded -> {
                hideLoader()
                mJoinQueueDialog?.dismiss()
                mQueueDialog =
                    InfoQueueDialog(renderState.queue, !renderState.isAlreadyInQueue, false)
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
        showLoader()

        mViewModel.getCurrentQueues("user", false)
        mViewModel.getHistoricalQueues("user", true)
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
            is Failure.QueuesNotFound -> {
                hideLoader()
                Toast.makeText(
                    this.context,
                    getString(R.string.queues_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Failure.JoinNotSuccessful -> {
                mQueueDialog?.dialog?.let {
                    if (it.isShowing)
                        mQueueDialog!!.dismiss()
                }
                Toast.makeText(
                    this.context,
                    getString(R.string.QueueJoiningError),
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

    override fun handleJoinQueueRequest(queue: Queue) {
        mViewModel.joinToQueue(queue.joinId)
    }

    override fun onNavigateQRFragment() {
        mJoinQueueDialog?.dismiss()
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_QRFragment)
    }

    override fun onJoinButtonClick(joinID: Int) {
        mViewModel.loadQueueToJoin(joinID)
    }
}
