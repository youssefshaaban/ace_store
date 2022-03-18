package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.example.mvvm_template.domain.entity.POINTS_PAYMENT_METHOD_TYPE
import com.example.mvvm_template.domain.entity.PaymentMethod


import com.example.mvvm_template.utils.*
import com.example.mvvm_template.utils.ConstantMethod.PLACE_ORDER
import com.payfort.fortpaymentsdk.FortSdk
import com.payfort.fortpaymentsdk.callbacks.FortCallBackManager
import com.payfort.fortpaymentsdk.callbacks.FortCallback
import com.payfort.fortpaymentsdk.callbacks.FortInterfaces
import com.payfort.fortpaymentsdk.domain.model.FortRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogPaymentMethodFragment : BaseFragment<DialogPaymentMethodFragmentBinding>() {

    companion object {
        fun newInstance() = DialogPaymentMethodFragment()
    }

    @Inject
    lateinit var appNavigator: AppNavigator
    lateinit var fortCallback: FortCallBackManager
    val filter: IntArray? by lazy {
        arguments?.getIntArray("filterMethod")
    }

    val amount: Double? by lazy {
        arguments?.getDouble("amount")
    }
    val currency: String? by lazy {
        arguments?.getString("currency")
    }
    val type: String? by lazy {
        arguments?.getString("type")
    }

    var clickListner: ((PaymentMethod) -> Unit)? = null
    val viewModel: PaymentMethodViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvMethodType.configGridRecycle(3, true)
        viewModel.getPaymentMethod()
        getViewDataBinding().type = type
        getViewDataBinding().currency = currency
        getViewDataBinding().cancel.setOnClickListener { dismiss() }
        fortCallback = FortCallback()
        getViewDataBinding().contentWallet.setOnClickListener {
            placeOrderByWallet(it.tag as? PaymentMethod)
        }
        getViewDataBinding().contentPoint.setOnClickListener {
            placeOrderByPonint(it.tag as? PaymentMethod)
        }
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
        dialog.setCancelable(false)
        return dialog
    }


    fun showDialog(fragmentManager: FragmentManager, clickPaymentMethod: (PaymentMethod) -> Unit) {
        clickListner = clickPaymentMethod
        show(fragmentManager, this.tag)
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
        observe(viewModel.paymentMethodLiveDate, ::handleResultData)
        observe(viewModel.loadingVisiblilty) {
            if (it) {
                getViewDataBinding().progress.toVisible()
            } else {
                getViewDataBinding().progress.toGone()
            }
        }
        observe(viewModel.error, ::handleFaluir)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        observe(viewModel.orderIdLiveData) {
            dismiss()
            appNavigator.navigateTo(Screen.HOME, Bundle().apply {
                putBoolean(GO_TO_HISTORY, true)
            })
        }
    }

    private fun placeOrderByWallet(payment: PaymentMethod?) {
        payment?.let {
            if (amount!! > payment.totalWalletAmount!!) {
                getViewDataBinding().cancel.showToast(
                    getString(R.string.txt_message_order_morthan_wallet_points),
                    1000
                )
            } else {
                // placeOrder
                viewModel.placeOrder(payment.id!!)
            }
        }
    }

    private fun placeOrderByPonint(payment: PaymentMethod?) {
        payment?.let {
            if (amount!! > payment.equivalentPointsAmount!!) {
                getViewDataBinding().cancel.showToast(
                    getString(R.string.txt_message_order_morthan_amount_points),
                    1000
                )
            } else {
                // placeOrder
                viewModel.placeOrder(payment.id!!)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fortCallback.onActivityResult(requestCode, resultCode, data);
    }

    private fun handleResultData(list: List<PaymentMethod>?) {
        list?.let {
            val list = it.filter { t -> !(filter?.contains(t.id ?: 0) ?: false) }
            if (PLACE_ORDER == type) {
                val wallet = list.find { t -> t.id == 5 }
                val point = list.find { t -> t.id == 6 }
                getViewDataBinding().wallet = wallet
                getViewDataBinding().point = point
            }
            getViewDataBinding().rvMethodType.adapter = PaymentMethodAdapter(list) { payment ->
                clickListner?.let {
                    it(payment)
                    dismiss()
                }
            }
        }
    }


}