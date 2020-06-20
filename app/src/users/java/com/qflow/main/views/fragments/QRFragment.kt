package com.qflow.main.views.fragments

import android.Manifest
import android.content.pm.PackageManager
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

    private val PERMISSION_CAMERA = 9999

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStates()
        startCamera()
    }

    private fun startCamera() {
        context?.let {
            if (requireActivity().packageManager.checkPermission(
                    Manifest.permission.CAMERA,
                    it.packageName
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                configureQR()
                qrDecoderView.startCamera()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
            }
        }
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
            is QRFragmentScreenState.QueueToJoinLoaded -> {
                mQueueDialog = InfoQueueDialog(screenState.queue, !screenState.isAlreadyInQueue, false)
                mQueueDialog!!.onAttachFragment(this)
                mQueueDialog!!.show(this.childFragmentManager, "JOINDIALOG")
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
        var isDialogShowing = mQueueDialog != null
        if(isDialogShowing)
            isDialogShowing = mQueueDialog!!.isVisible
        if (isProcessing.compareAndSet(false, true) && !isDialogShowing ) {
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
            Toast.makeText(
                this.context,
                getString(R.string.QRLoadingErrorMessage),
                Toast.LENGTH_SHORT
            )
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

    override fun handleJoinQueueRequest(queue: Queue) {
        mQueueDialog?.dismiss()
        mViewModel.joinToQueue(queue.id)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CAMERA ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    view?.findNavController()?.navigate(R.id.action_QRFragment_self)
                }
            else {
                    Toast.makeText(
                        this.context,
                        getString(R.string.NoPermissionsCamera),
                        Toast.LENGTH_SHORT
                    ).show()
                    view?.findNavController()?.navigate(R.id.action_QRFragment_to_homeFragment)
                }
        }
    }
}
