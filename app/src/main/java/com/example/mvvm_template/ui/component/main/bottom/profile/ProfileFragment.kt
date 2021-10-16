package com.example.mvvm_template.ui.component.main.bottom.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ProfileFragmentBinding
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.ui.component.login.GenerateOtpActivity
import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {
    val sharedViewModel: MainViewModel by activityViewModels()
    @Inject
    lateinit var navigator: AppNavigator
    companion object {
        fun newInstance() = ProfileFragment()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.title.value=getString(R.string.title_profile)
        getViewDataBinding().contentInfoAccount.setOnClickListener {
            if (App.getUser()==null){
                showMustSignInPopUp()
            }else{

            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
    }

    override fun observeViewModel() {
        observe(sharedViewModel.observProfile,::handelDataStatVerifyOTP)
    }

    private fun handelDataStatVerifyOTP(dataState: DataState<Profile>) {
        when (dataState) {
            is DataState.Success -> {
                getViewDataBinding().name.text=dataState.data.name
                getViewDataBinding().imageView.loadImage(dataState.data.imagePath,R.drawable.bg_no_image)
                getViewDataBinding().phone.text=dataState.data.mobileNumber
            }
            is DataState.Error -> {
               handleFaluir(dataState.error)
            }
        }
    }
    private fun showMustSignInPopUp() {
        MaterialAlertDialogBuilder(getViewDataBinding().root.context)
            .setTitle(getString(R.string.login))
            .setMessage(getString(R.string.must_login))
            .setPositiveButton(getString(R.string.ok)){
                    dialog,_->
                // open_setting_screen
                openSignIn()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)){
                    dialog,_->
                dialog.dismiss()
            }
            .show()
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
        navigator.navigateTo(Screen.VERIFY_CODE,null)
    }


}