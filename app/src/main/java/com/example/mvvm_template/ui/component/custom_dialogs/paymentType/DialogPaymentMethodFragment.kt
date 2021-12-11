package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.DialogPaymentMethodFragmentBinding
import com.example.mvvm_template.utils.configGridRecycle

class DialogPaymentMethodFragment : BaseFragment<DialogPaymentMethodFragmentBinding>() {

    companion object {
        fun newInstance() = DialogPaymentMethodFragment()
    }
    val list= listOf(PaymentMethod(MADA,R.drawable.ic_mada),PaymentMethod(VISA,R.drawable.bt_ic_visa),PaymentMethod(
        MASTER,R.drawable.ic_master))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvMethodType.configGridRecycle(3,true)
        getViewDataBinding().rvMethodType.adapter=PaymentMethodAdapter(list){

        }
        getViewDataBinding().cancel.setOnClickListener { dismiss() }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_payment_method_fragment
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
        val height = (resources.displayMetrics.heightPixels * 0.6).toInt()
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            height
        )
        window.attributes.windowAnimations = R.style.PopupAnimation
        window.setGravity(Gravity.BOTTOM)
        super.onResume()
    }

    override fun observeViewModel() {

    }


}