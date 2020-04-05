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
import com.qflow.main.views.screenstates.ProfileFragmentScreenState
import com.qflow.main.views.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.creators.item_profile.*


class ProfileFragment : Fragment() {

    lateinit var viewModel: ProfileViewModel
    /* val binding: ProfileFragmentScreenState = DataBindingUtil.inflate(
         inflater, R.layout.profile_fragment, container, false)*/
    //val adapter = ProfileAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeListeners()
    }

    private fun initializeListeners() {
        initializeButtons()
        //viewModel.screenState.observe(::getLifecycle, ::updateUI)
        //viewModel.failure.observe(::getLifecycle, ::handleErrors)
    }

    private fun initializeButtons() {
        //img_profile.setImageResource()
        btn_add.setOnClickListener {
            view?.let {
                view?.findNavController()!!
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToCreateQueueFragment())
            }
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

    private fun renderScreenState(renderState: ProfileFragmentScreenState) {
        when (renderState) {
            is ProfileFragmentScreenState.AccessProfile -> {
                view?.let {
                    view?.findNavController()!!
                    /*.navigate(
                   ¿Siguiente vista?
                )
                }*/
                }
            }
        }
    }

    private fun updateUI(screenState: ScreenState<ProfileFragmentScreenState>?) {
        when (screenState) {
            ScreenState.Loading -> {
            }
            is ScreenState.Render -> renderScreenState(screenState.renderState)
        }
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner, androidx.lifecycle.Observer
        {
            updateUI(it)
        })
    }

}


