package com.example.mvvm_template.ui.component.main.bottom.purchase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.CART_COUNT
import com.example.mvvm_template.core.common.CART_UPDATE
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.FragmentPurchasedBinding
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.ui.component.auth.login.GenerateOtpActivity
import com.example.mvvm_template.ui.component.custom_dialogs.order_detail.DialogOrderDetailFragment
import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PurchasedFragment : BaseFragment<FragmentPurchasedBinding>() {

    val sharedViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var navigator: AppNavigator
    val viewModel: PurchesdViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    private fun openSignIn() {
        startActivity(
            GenerateOtpActivity.getIntent(requireContext())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        activity?.finishAffinity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().contentEmpty.emptyRecycle.configRecycle(true)
        if (App.getUser() != null) {
            viewModel.getMyOrders()
            getViewDataBinding().contentNotLogin.toGone()
            getViewDataBinding().contentRecycle.toVisible()
        } else {
            getViewDataBinding().contentRecycle.toGone()
            getViewDataBinding().contentNotLogin.toVisible()
        }
        getViewDataBinding().login.setOnClickListener {
            openSignIn()
        }
        sharedViewModel.title.value = getString(R.string.title_purchases)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_purchased
    }

    override fun observeViewModel() {
        observe(viewModel.loaderVisibilityLiveData) {
            if (it)
                showLoading()
            else hideLoading()
        }
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.successReorderLiveData) {
            if (it) {
                viewModel.getCart()
            }
        }
        observe(viewModel.cartLiveData, ::handleCartLiveDate)
        observe(viewModel.myOrdersLiveData) {
            getViewDataBinding().contentEmpty.emptyRecycle.adapter =
                PurchasedCardAdapter(it, clickReorder = {
                    viewModel.reOrder(it)
                }) {
                    DialogOrderDetailFragment.newInstance().apply {
                        arguments = Bundle().apply { putInt("id", it.id!!) }
                    }.show(
                        childFragmentManager,
                        DialogOrderDetailFragment::class.java.canonicalName
                    )
                }
        }
    }

    private fun handleCartLiveDate(dataState: DataState<Cart?>) {
        when (dataState) {
            is DataState.Success -> {
                val intent = Intent(CART_UPDATE)
                intent.putExtra(CART_COUNT, dataState.data?.products?.size)
                LocalBroadcastManager.getInstance(getViewDataBinding().root.context)
                    .sendBroadcast(intent)
                navigator.navigateTo(Screen.CART, null)
            }
        }
    }
}