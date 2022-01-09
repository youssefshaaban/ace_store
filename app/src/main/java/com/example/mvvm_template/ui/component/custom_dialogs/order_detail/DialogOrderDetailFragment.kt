package com.example.mvvm_template.ui.component.custom_dialogs.order_detail

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
import com.example.mvvm_template.databinding.DialogOrderDetailFragmentBinding
import com.example.mvvm_template.databinding.DialogPaymentMethodFragmentBinding
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogOrderDetailFragment : BaseFragment<DialogOrderDetailFragmentBinding>() {

    companion object {
        fun newInstance() = DialogOrderDetailFragment()
    }

    val viewModel: OrderDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvOrders.configRecycle(true)
        viewModel.getOrderById(arguments?.getInt("id", 0)!!)
        getViewDataBinding().cancel.setOnClickListener { dismiss() }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_order_detail_fragment
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
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it)
                showLoading()
            else
                hideLoading()
        }
        observe(viewModel.orderDetailLiveData){
            setData(it)
        }
    }

    private fun setData(it: Order) {
        it.orderDetails?.let {  getViewDataBinding().rvOrders.adapter=OrderItemDetailAdapter(it)}
        getViewDataBinding().orderNum.text=it.id.toString()
        getViewDataBinding().orderVat.text=it.tax.toString()
        getViewDataBinding().orderPrice.text=it.totalPrice.toString()
        getViewDataBinding().orderTotal.text=it.totalPriceBeforDiscount.toString()
        getViewDataBinding().orderDiscount.text=it.discountValue.toString()
    }


}