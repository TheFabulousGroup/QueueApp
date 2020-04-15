package com.qflow.main.views.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.screenstates.JoinQueueScreenStates
import com.qflow.main.views.screenstates.SignUpFragmentScreenState
import com.qflow.main.views.viewmodels.JoinQueueViewModel
import kotlinx.android.synthetic.users.dialog_join_queue.*
import kotlinx.android.synthetic.users.fragment_signup.*

class JoinQueueDialog : DialogFragment(){

    private val viewModel: JoinQueueViewModel by viewModel()


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

        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(this.viewLifecycleOwner, ::updateUI)
        viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    @SuppressLint("ResourceAsColor")
    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.PASSWORDS_NOT_THE_SAME -> {
                        Toast.makeText(
                            this.context, "Passwords do not match", Toast.LENGTH_LONG).show()
                        this.context?.let { ContextCompat.getColor(it, R.color.errorRedColor) }?.let {
                            password.background.setTint(it)
                            repeat_Password.background.setTint(it)
                        }
                    }
                }
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
            is JoinQueueScreenStates.UserCreatedCorrectly -> {
                //Toast.makeText(this.context, renderState.id.toString(), Toast.LENGTH_LONG).show()
                view?.findNavController()?.navigate(R.id.action_SignUpFragment_to_navigation_home)
            }
        }

    }
}
