package com.example.mvvm_template.ui.component.main.bottom.offer

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.DialogOfferFragmentBinding
import com.example.mvvm_template.ui.component.card_categories.ProductCategoryAdapter

import com.example.mvvm_template.utils.configGridRecycle

class DialogOfferFragment : BaseFragment<DialogOfferFragmentBinding>() {

    companion object {
        fun newInstance() = DialogOfferFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2, true)
        getViewDataBinding().content.emptyRecycle.adapter=ProductCategoryAdapter({}){
            
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_offer_fragment
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
            height
        )
        window.attributes.windowAnimations = R.style.PopupAnimation
        window.setGravity(Gravity.BOTTOM)
        super.onResume()
    }

    override fun observeViewModel() {

    }


}