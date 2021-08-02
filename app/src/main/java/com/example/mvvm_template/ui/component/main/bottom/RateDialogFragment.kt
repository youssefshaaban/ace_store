package com.example.mvvm_template.ui.component.main.bottom

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.DialogOfferFragmentBinding
import com.example.mvvm_template.databinding.DialogRateLayoutBinding
import com.example.mvvm_template.ui.base.BaseFragment
import com.example.mvvm_template.utils.configGridRecycle
import com.example.mvvm_template.utils.configRecycle

class RateDialogFragment : BaseFragment<DialogRateLayoutBinding>() {

    companion object {
        fun newInstance() = RateDialogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvProduct.configRecycle(true)
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_rate_layout
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
        val height = (resources.displayMetrics.heightPixels * 0.8).toInt()
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