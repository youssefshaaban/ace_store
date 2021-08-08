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
import com.example.mvvm_template.ui.component.login.LoginActivity
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {
    val sharedViewModel: MainViewModel by sharedViewModel()
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

    private fun showMustSignInPopUp(content: String) {
//        Builder(getBaseActivity())
//            .setTitle(resources.getString(R.string.login))
//            .setContent(content)
//            .setContentColor(resources.getColor(R.color.))
//            .setPositiveButton(R.string.login) { popUp ->
//                popUp.dismiss()
//                openSignIn()
//            }
//            .setNegativeButton(resources.getString(R.string.cancel), null)
//            .setCanceledOnTouchOutside(true)
//            .create()
    }

    private fun openSignIn() {
        LoginActivity.getIntent(getViewDataBinding().root.context)
    }


}