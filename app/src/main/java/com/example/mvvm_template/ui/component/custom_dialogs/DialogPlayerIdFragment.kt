package com.example.mvvm_template.ui.component.custom_dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.GO_TO_HISTORY
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.DialogPaymentMethodFragmentBinding
import com.example.mvvm_template.databinding.DialogPlayerIdFragmentBinding

import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogPlayerIdFragment : BaseFragment<DialogPlayerIdFragmentBinding>() {

    companion object {
        fun newInstance() = DialogPlayerIdFragment()
    }
    lateinit var textPlayerID:(String,DialogPlayerIdFragment)->Unit
    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().cancel.setOnClickListener { dismiss() }
        getViewDataBinding().txtConfirm.setOnClickListener {
            if (getViewDataBinding().etPlayerId.text.isNullOrEmpty()){
                getViewDataBinding().etPlayerId.showToast(
                    getString(R.string.message_should_enter_player_id),
                    1000
                )
            }else{
                textPlayerID(getViewDataBinding().etPlayerId.text.toString(),this)
            }
        }
    }

    public fun showDialog(fragmentManager: FragmentManager,txtPlayerId:(String,DialogPlayerIdFragment)->Unit){
        this.textPlayerID=txtPlayerId
        show(fragmentManager,DialogPlayerIdFragment::class.java.name)
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_player_id_fragment
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
        dialog.setCancelable(false)
        return dialog
    }


    override fun onResume() {
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
     //   val height = (resources.displayMetrics.heightPixels * 0.4).toInt()
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.attributes.windowAnimations = R.style.PopupAnimation
        window.setGravity(Gravity.BOTTOM)
        super.onResume()
    }

    override fun observeViewModel() {

    }

}