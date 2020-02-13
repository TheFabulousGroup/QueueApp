package com.queueapp.main.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.queueapp.main.R
import com.queueapp.main.database.user.AppDatabase
import com.queueapp.main.databinding.ProfileFragmentBinding
import com.queueapp.main.views.viewmodels.ProfileViewModel
import com.queueapp.main.views.viewmodels.ProfileViewModelFactory
import kotlinx.android.synthetic.main.item_profile.*


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ProfileViewModelFactory
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.profile_fragment,
            container,
            false
        )
        val args = arguments?.let { ProfileFragmentArgs.fromBundle(it) }
        viewModelFactory = args?.userId?.let { ProfileViewModelFactory(it, AppDatabase.getInstance(activity!!.application)) }!!
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel::class.java)
        binding.lifecycleOwner = this
        binding.profileViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressPercent.setOnClickListener{
            shareSuccess()
        }
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, viewModel.currentUser.value?.username )
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}
