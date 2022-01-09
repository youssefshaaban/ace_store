package com.example.mvvm_template.ui.component.main.wallet

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityPaymentBinding
import com.example.mvvm_template.databinding.ActivityWalletBinding
import com.example.mvvm_template.ui.component.custom_dialogs.order_detail.DialogOrderDetailFragment
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.ui.component.main.bottom.purchase.PurchasedCardAdapter
import com.example.mvvm_template.ui.component.payment.CHARGE_WALLET
import com.example.mvvm_template.ui.component.payment.PaymentActivity
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : BaseActivity<ActivityWalletBinding>() {
    val viewModel: WalletViewModel by viewModels()
    var amount: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.title.text = getString(R.string.menu_wallet)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().rvTransaction.configRecycle(true)
        viewModel.getWallet()
        getViewDataBinding().chargeWallet.setOnClickListener {
            goToChargeWallet()
        }
        setUpObservable()
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

// Set up the input
        val input = EditText(this)
        input.gravity=Gravity.CENTER
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(getString(R.string.amount))
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which ->
            // Here you get get input text from the Edittext
            amount = input.text.toString().toDouble()
            DialogPaymentMethodFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putIntArray("filterMethod", intArrayOf(5, 6))
                    }
                }
                .showDialog(supportFragmentManager) {
                    launchSomeActivity.launch(
                        PaymentActivity.getIntent(this).putExtra(
                            "type",
                            CHARGE_WALLET
                        ).putExtra("amount", amount)
                            .putExtra("paymentId", it.id)
                            .putExtra("currency", getString(R.string.currencyKSA))
                    )
                }
            dialog.dismiss()
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                viewModel.getWallet()
            }
        }
}