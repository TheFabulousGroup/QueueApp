package com.qflow.main.views.fragments

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.views.dialogs.InfoQueueDialog
import com.qflow.main.views.screenstates.QRFragmentScreenState
import com.qflow.main.views.viewmodels.QRFragmentViewModel
import kotlinx.android.synthetic.users.fragment_qr.*
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.atomic.AtomicBoolean

class QRFragment : Fragment(), QRCodeReaderView.OnQRCodeReadListener, InfoQueueDialog.OnJoinClick {

    private val mViewModel: QRFragmentViewModel by viewModel()
    private var isProcessing: AtomicBoolean = AtomicBoolean(false)
    private var mQueueDialog: InfoQueueDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStates()
        configureQR()
    }


    private fun configureQR() {
        qrDecoderView.invalidate()
        qrDecoderView.setOnQRCodeReadListener(this)
        qrDecoderView.setQRDecodingEnabled(true)
        qrDecoderView.setAutofocusInterval(1000L)
        qrDecoderView.setTorchEnabled(true)
        qrDecoderView.setFrontCamera()
        qrDecoderView.setBackCamera()
    }


    private fun initStates() {
        mViewModel.screenState.observe(::getLifecycle, ::updateUI)
        mViewModel.failure.observe(::getLifecycle, ::handleErrors)
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
        isProcessing.set(false)

    }


    private fun renderScreenState(screenState: QRFragmentScreenState) {
        hideLoader()
        when (screenState) {
            is QRFragmentScreenState.QueueLoaded -> {
                mQueueDialog = InfoQueueDialog(screenState.queue, true)
                mQueueDialog
            }
            is QRFragmentScreenState.JoinedQueue ->
                view?.findNavController()?.navigate(R.id.action_QRFragment_to_homeFragment)

        }
    }


    private fun updateUI(barscanstate: ScreenState<QRFragmentScreenState>) {
        when (barscanstate) {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> {
                renderScreenState(barscanstate.renderState)
            }
        }
    }

    private fun showLoader() {
        loading_bar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loading_bar.visibility = View.INVISIBLE
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        if (isProcessing.compareAndSet(false, true)) {
            if (text != null)
                handleQRQueue(text)
            else
                isProcessing.set(false)
        }
    }

    private fun handleQRQueue(text: String) {
        try {
            val jsonObject = JSONObject(text)
            if (jsonObject.has("QflowQueue")) {
                mViewModel.loadQueueToJoin(jsonObject.getInt("QflowQueue"))
            } else
                throw JSONException("QR does not contain a QFlowQueue")
        } catch (ex: JSONException) {
            Toast.makeText(this.context, getString(R.string.QRLoadingErrorMessage), Toast.LENGTH_SHORT)
                .show()
        } finally {
            isProcessing.set(false)
        }
    }

    override fun onResume() {
        super.onResume()
        qrDecoderView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrDecoderView.stopCamera()
    }

    override fun handleQRCall(queue: Queue) {
        mQueueDialog?.dismiss()
        mViewModel.joinToQueue(queue.id)
    }
}
