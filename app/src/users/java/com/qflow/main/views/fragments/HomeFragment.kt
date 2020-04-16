package com.qflow.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.qflow.main.R
import com.qflow.main.core.Failure
import com.qflow.main.core.ScreenState
import com.qflow.main.utils.enums.ValidationFailureType
import com.qflow.main.views.adapters.ProfileAdapter
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.viewmodels.HomeViewModel
import kotlinx.android.synthetic.users.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val mViewModel: HomeViewModel by viewModel()

    /*val binding: ProfileFragmentScreenState = DataBindingUtil.inflate(
         inflater, R.layout.fragment_home, container, false)*/
    val adapter = ProfileAdapter()


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
    }

    private fun initializeListeners() {
        initializeButtons()
        mViewModel.screenState.observe(::getLifecycle, ::updateUI)
        //viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {
        //img_profile.setImageResource()

        btn_join_queue.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_joinQueueDialog)

        }
    }

    private fun handleErrors(failure: Failure?) {
        when (failure) {
            is Failure.ValidationFailure -> {
                when (failure.validationFailureType) {
                    ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY -> {//Cambiar por profile
                        //TODO añadir aqui que hacer cuando el validador de fallo

                    }
                }
            }
        }
    }

    private fun renderScreenState(renderState: HomeFragmentScreenState) {
        when (renderState) {
            is HomeFragmentScreenState.AccessProfile -> {
                view?.let {
                    view?.findNavController()!!
                    /*.navigate(
                    ProfileFragmentDirections
                        .actionLoginFragmentToProfileFragment(renderState.id)

                )*/
                }
            }
        }
    }

    private fun updateUI(screenState: ScreenState<HomeFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun initializeObservers() =
        mViewModel.screenState.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            updateUI(it)
        })

}
