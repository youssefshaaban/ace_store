package com.example.mvvm_template.ui.component.rate

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
import com.example.mvvm_template.databinding.DialogRateLayoutBinding
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.entity.RateOrder
import com.example.mvvm_template.domain.interactor.order.RateOrderUseCse
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

const val RATE_ORDER = "rate_order"

@AndroidEntryPoint
class RateDialogFragment : BaseFragment<DialogRateLayoutBinding>() {

    var rateOrder: RateOrder? = null
    val viewModel: RateViewModel by viewModels()

    companion object {
        fun newInstance(rateOrder: RateOrder) = RateDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(RATE_ORDER, rateOrder)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rateOrder = arguments?.getParcelable(RATE_ORDER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvProduct.configRecycle(true)
        getViewDataBinding().rvProduct.adapter =
            RateProductAdapter(rateOrder?.products ?: ArrayList())
        getViewDataBinding().ignore.setOnClickListener {
            callIgnoreApi()
        }
        getViewDataBinding().sendRate.setOnClickListener {
            callRateApi()
        }

    }

    private fun callRateApi() {
        viewModel.rateOrder(
            RateDTO(
                orderId = rateOrder?.orderId,
                comment = getViewDataBinding().comment.text.toString(),
                grade = getViewDataBinding().rate.rating.toInt()
            )
        )
    }

    private fun callIgnoreApi() {
        viewModel.skipRateOrder(
            RateDTO(
                orderId = rateOrder?.orderId,
            )
        )
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
        val height = (resources.displayMetrics.heightPixels * 0.75).toInt()
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            height
        )
        window.attributes.windowAnimations = R.style.PopupAnimation
        window.setGravity(Gravity.BOTTOM)
        super.onResume()
    }

    override fun observeViewModel() {
        observe(viewModel.successLiveData) {
            if (it) {
                dismiss()
            }
        }
        observe(viewModel.validationExceptionLiveData, ::handleExceptionValidation)
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    private fun handleExceptionValidation(throwable: Throwable) {
        when (throwable) {
            is RateOrderUseCse.MissingOrderId -> {
                 getViewDataBinding().ignore.showToast(getString(R.string.txt_missing_order_id),1000)
            }
            is RateOrderUseCse.MissingRateOrderComment -> {
                getViewDataBinding().ignore.showToast(getString(R.string.txt_missing_order_comment),1000)
            }
            is RateOrderUseCse.MissingRateOrderValue -> {
                getViewDataBinding().ignore.showToast(getString(R.string.txt_missing_order_value),1000)
            }
        }
    }


}