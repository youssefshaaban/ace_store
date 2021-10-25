package com.example.mvvm_template.ui.component.auth.logout

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseDialog
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.DialogLogoutBinding
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogoutDialog : BaseDialog<DialogLogoutBinding>() {
    lateinit var binding: DialogLogoutBinding
    val viewModel: LogoutViewModel by viewModels()

    @Inject
    lateinit var navigator: AppNavigator
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        binding.title.text = context?.resources?.getString(R.string.logout)
        binding.description.text = context?.resources?.getString(R.string.message_logout)
        binding.txtYes.setOnClickListener {
            viewModel.logOut()
        }
        binding.txtNo.setOnClickListener {
            dismiss()
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }


    override fun getLayoutId(): Int {
        return R.layout.dialog_logout
    }

    override fun observeViewModel() {
        observe(viewModel.observeSuccess, ::handleResultLogout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleResultLogout(resource: DataState<Boolean>) {
        when (resource) {
            is DataState.Success -> {
                binding.progress.toVisible()
                SavePrefs(getViewDataBinding().description.context, User::class.java).clear()
                navigator.navigateTo(Screen.GENERATE_OTP, null)
                activity?.finishAffinity()
                dismiss()
            }
            is DataState.Error -> {
                binding.progress.toGone()
                binding.txtNo.isEnabled = true
                binding.txtYes.isEnabled = true
                handleFaluir(resource.error)
            }
            is DataState.Loading -> {
                binding.progress.toVisible()
                binding.txtNo.isEnabled = true
                binding.txtYes.isEnabled = true
            }
        }
    }

}