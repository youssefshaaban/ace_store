package com.example.mvvm_template.ui.component.main.wallet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityWalletBinding
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.utils.LogUtil
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.payfort.fortpaymentsdk.FortSdk
import com.payfort.fortpaymentsdk.callbacks.FortCallBackManager
import com.payfort.fortpaymentsdk.callbacks.FortInterfaces
import com.payfort.fortpaymentsdk.domain.model.FortRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : BaseActivity<ActivityWalletBinding>() {
    val viewModel: WalletViewModel by viewModels()
    var amount: Double = 0.0
    lateinit var selectedPayment: PaymentMethod
    val REQUEST_CODE_PAY = 5
    var fortCallback: FortCallBackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.title.text = getString(R.string.menu_wallet)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().rvTransaction.configRecycle(true)
        viewModel.getWallet()
        fortCallback = FortCallBackManager.Factory.create()
        getViewDataBinding().chargeWallet.setOnClickListener {
            goToChargeWallet()
        }
        setUpObservable()
    }


    private fun payWithSdk(fortRequest: FortRequest) {
        FortSdk.getInstance().registerCallback(this,
            fortRequest,
            FortSdk.ENVIRONMENT.TEST,
            REQUEST_CODE_PAY,
            fortCallback,
            true,
            object : FortInterfaces.OnTnxProcessed {
                override fun onCancel(
                    requestParamsMap: MutableMap<String, Any>?,
                    p1: MutableMap<String, Any>?
                ) {
                    LogUtil.error("payment cancel", requestParamsMap.toString())
                    LogUtil.error("payment cancel", p1.toString())
                }

                override fun onSuccess(
                    requestParamsMap: MutableMap<String, Any>?,
                    p1: MutableMap<String, Any>?
                ) {
                    p1?.let {
                        LogUtil.error("payment success", requestParamsMap.toString())
                        LogUtil.error("payment success", p1.toString())
                        viewModel.chargeWallet(
                            amount,
                            selectedPayment.id!!,
                            p1["merchant_reference"],
                            p1["fort_id"]
                        )
                    }
                }

                override fun onFailure(
                    requestParamsMap: MutableMap<String, Any>?,
                    p1: MutableMap<String, Any>?
                ) {
                    LogUtil.error("payment onFailure", requestParamsMap.toString())
                    LogUtil.error(" payment onFailure", p1.toString())
                }

            })
    }


    private fun setUpObservable() {
        observe(viewModel.loaderVisibilityLiveData) {
            if (it)
                showLoading()
            else hideLoading()
        }
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.walletLiveData) { wallet ->
            wallet?.let {
                getViewDataBinding().total.text = "${it.amount}  ${it.currency.name}"
                getViewDataBinding().rvTransaction.adapter =
                    WalletTransactionAdapter(it.transactions)
            }
        }
        observe(viewModel.successChargeLiveData) {
            if (it) {
                viewModel.getWallet()
            }
        }
        observe(viewModel.fortRequestLiveData, ::payWithSdk)
    }

    private fun goToChargeWallet() {
        showDialogTakeAmount()
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, WalletActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_wallet
    }

    private fun showDialogTakeAmount() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.set_your_amount))
        val input = EditText(this)
        input.gravity = Gravity.CENTER
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(getString(R.string.amount))
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, _ ->
            // Here you get get input text from the Edittext
            amount = input.text.toString().toDouble()
            DialogPaymentMethodFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putIntArray("filterMethod", intArrayOf(5, 6))
                    }
                }
                .showDialog(supportFragmentManager) {
                    selectedPayment = it
                    viewModel.getForRequest(amount)
                }
            dialog.dismiss()
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                viewModel.getWallet()
            }
        }
}