package com.example.mvvm_template.ui.component.payment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.GO_TO_HISTORY
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityPaymentBinding
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

val PLACE_ORDER = "placeOrder"
val CHARGE_WALLET = "charge_wallet"

@AndroidEntryPoint
class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {
    val viewModel: PaymentViewModel by viewModels()
    val paymentId: Int by lazy {
        intent.getIntExtra("paymentId", 0)
    }
    val type: String? by lazy {
        intent.getStringExtra("type")
    }
    val amount: Double? by lazy {
        intent.getDoubleExtra("amount", 0.0)
    }
    val currency: String? by lazy {
        intent.getStringExtra("currency")
    }

    @Inject
    lateinit var appNavigator: AppNavigator
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpObservable()
        getViewDataBinding().appBar.title.text = getText(R.string.payment)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().txtAmount.text="$amount $currency"

        getViewDataBinding().pay.setOnClickListener {
            goToPay()
        }
    }

    private fun goToPay() {
        if (validateInput()) {
            if (type == PLACE_ORDER) {
                viewModel.placeOrder(paymentId, getCardData())
            } else {
                viewModel.chargeWallet(amount!!,paymentId, getCardData())
            }
        }

    }

    private fun getCardData(): PaymentViewModel.CardData = PaymentViewModel.CardData(
        number = getViewDataBinding().cardNumber.text.toString().toLong(),
        name = getViewDataBinding().cardHolderName.text.toString(),
        exp_month = getViewDataBinding().expMonth.text.toString().toInt(),
        exp_year = getViewDataBinding().expYear.text.toString().toInt(),
        cvc = getViewDataBinding().cvv.text.toString().toInt()
    )

    private fun setUpObservable() {
        observe(viewModel.orderIdLiveData) {
            appNavigator.navigateTo(Screen.HOME, Bundle().apply {
                putBoolean(GO_TO_HISTORY, true)
            })
        }
        observe(viewModel.successChargeLiveData){
            if (it){
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }
        observe(viewModel.throwableLive){
            getViewDataBinding().appBar.title.showToast(it.message?:"",1000)
        }
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_payment
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, PaymentActivity::class.java)
    }

    private fun validateInput(): Boolean {
        if (getViewDataBinding().cardNumber.text.toString().length != 16) {
            Toast.makeText(this, getString(R.string.must_enter_valid_cart), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (getViewDataBinding().cardHolderName.text.isEmpty()) {
            Toast.makeText(this, getString(R.string.muster_enter_name), Toast.LENGTH_SHORT).show()
            return false
        }
        if (getViewDataBinding().expMonth.text.isEmpty() || getViewDataBinding().expMonth.text.length != 2) {
            Toast.makeText(this, getString(R.string.must_enter_valid_month), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (getViewDataBinding().expYear.text.isEmpty() || getViewDataBinding().expYear.text.length != 4) {
            Toast.makeText(this, getString(R.string.must_enter_valid_year), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (getViewDataBinding().cvv.text.isEmpty() || getViewDataBinding().cvv.text.length != 3) {
            Toast.makeText(this, getString(R.string.must_enter_valid_cvv), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

}