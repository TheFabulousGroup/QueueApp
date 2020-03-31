package com.qflow.main.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.qflow.main.R
import com.qflow.main.views.adapters.ProfileAdapter
import com.qflow.main.views.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.item_profile.*


/**
 * Old view used for the signin (pending to be deleted)
 * */

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    val adapter = ProfileAdapter()
}
