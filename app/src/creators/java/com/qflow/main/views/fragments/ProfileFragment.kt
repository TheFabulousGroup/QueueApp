
package com.qflow.main.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.qflow.main.R
import com.qflow.main.databinding.ProfileFragmentBinding
import com.qflow.main.views.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.item_profile.*


/**
 * Old view used for the signin (pending to be deleted)
 * */


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
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
        //Example of how to get vars other view
//        val args = arguments?.let { ProfileFragmentArgs.fromBundle(it) }

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
            .putExtra(Intent.EXTRA_TEXT, viewModel.currentUserDB.value?.username )
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}
