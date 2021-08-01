package com.example.mvvm_template.ui.component.main.bottom.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ProfileFragmentBinding
import com.example.mvvm_template.ui.base.BaseFragment
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {
    val sharedViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = ProfileFragment()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.title.value=getString(R.string.title_profile)
    }

    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
    }

    override fun observeViewModel() {

    }

}