package com.example.mvvm_template.ui.component.main.championships

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.DialogJoinChallengeFragmentBinding
import com.example.mvvm_template.databinding.DialogOrderDetailFragmentBinding
import com.example.mvvm_template.databinding.DialogPaymentMethodFragmentBinding
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogJoinChallangelFragment : BaseFragment<DialogJoinChallengeFragmentBinding>() {

    val viewModel:JoinChallengeViewModel by viewModels()
    val challengId:Int? by lazy {
        arguments?.getInt("id")
    }
    companion object {
        fun newInstance() = DialogJoinChallangelFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().join.setOnClickListener {
            if (validateInput()){
                viewModel.joinChallenge(RequestChallengeDTo(challengId!!, playerId = getViewDataBinding().playerId.text.toString(), playerName = getViewDataBinding().userName.text.toString()))
            }
        }

    }

    private fun validateInput(): Boolean {
        if (getViewDataBinding().userName.text.isNullOrEmpty()){
            getViewDataBinding().join.showToast(getString(R.string.txt_message_enter_playername),1000)
            return false
        }
        else if (getViewDataBinding().playerId.text.isNullOrEmpty()){
            getViewDataBinding().join.showToast(getString(R.string.txt_message_enter_player_id),1000)
            return false
        }
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_join_challenge_fragment
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


    override fun onResume() {
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.attributes.windowAnimations = R.style.PopupAnimation
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }

    override fun observeViewModel() {
        observe(viewModel.loaderVisibilityLiveData){
            if (it){
                getViewDataBinding().join.toGone()
                getViewDataBinding().progress.toVisible()
            }else{
                getViewDataBinding().join.toVisible()
                getViewDataBinding().progress.toGone()
            }
        }

        observe(viewModel.successLiveData){
            if (it){
                getViewDataBinding().join.showToast(getString(R.string.save_succes_join_challenge),1000)
                dismiss()
            }
        }
        observe(viewModel.faluireLiveData,::handleFaluir)
    }


}