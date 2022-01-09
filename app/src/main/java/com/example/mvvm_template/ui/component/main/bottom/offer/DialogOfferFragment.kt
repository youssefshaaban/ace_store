package com.example.mvvm_template.ui.component.main.bottom.offer

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.DialogOfferFragmentBinding
import com.example.mvvm_template.domain.entity.POINTS_PAYMENT_METHOD_TYPE
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.entity.WALLET_PAYMENT_METHOD_TYPE
import com.example.mvvm_template.ui.component.card_categories.ProductCategoryAdapter
import com.example.mvvm_template.ui.component.card_categories.ProductsViewModel
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.ui.component.payment.PLACE_ORDER
import com.example.mvvm_template.ui.component.payment.PaymentActivity
import com.example.mvvm_template.utils.*

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogOfferFragment : BaseFragment<DialogOfferFragmentBinding>() {
    var pagNumber: Int = FIRST_PAGE
    val viewModel: ProductsViewModel by viewModels()
    var product: Product? = null

    @Inject
    lateinit var navigator:AppNavigator
    companion object {
        fun newInstance() = DialogOfferFragment()
    }

    @Inject
    lateinit var appNavigator: AppNavigator
    private val productCategoryAdapter by lazy {
        ProductOfferAdapter(::handleClickProduct, ::handleClickBuy)
    }

    private fun handleClickBuy(product: Product) {
        this.product=product
        if (checkUserLogin()){
            DialogPaymentMethodFragment().apply {
                arguments=Bundle().apply {
                    putDouble("amount",product.priceAfterDiscount!!)
                    putString("currency",product.currency?.name)
                    putString("type", PLACE_ORDER)
                }
            }.showDialog(
                childFragmentManager
            ){
                startActivity(PaymentActivity.getIntent(requireContext()).putExtra("amount",product.priceAfterDiscount)
                    .putExtra("currency",product.currency?.name)
                    .putExtra("type",PLACE_ORDER)
                )
            }
        }
    }


    private fun handleClickProduct(product: Product) {
        appNavigator.navigateTo(Screen.PRODUCT_DETAIL, Bundle().apply {
            putInt("ID", product.id)
            putBoolean("isAddCart", product.isAtCart)
        })
    }

    private fun handleLoadMore() {
        if (!viewModel.stopLoadingMore) {
            pagNumber += 1
            viewModel.getProducts(offer = true, pageIndex = pagNumber)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2, true)
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        getViewDataBinding().content.emptyRecycle.adapter=productCategoryAdapter
        viewModel.getProducts(offer = true, pageIndex = pagNumber)
        getViewDataBinding().content.emptyRecycle.addEndlessScroll(::handleLoadMore)
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
        observe(viewModel.productsLiveData, ::handleDateStatCategory)
        observe(viewModel.loadinVisibilityObserve) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        observe(viewModel.notifyPositionCartLiveDate, ::handlDataChangeCart)
        observe(viewModel.errorFaliureCallingLiveDate) { faluir ->
            faluir?.let {
                handleFaluir(it)
            }
        }
    }

    private fun handleDateStatCategory(products: List<Product>) {
        productCategoryAdapter.submitList(productCategoryAdapter.currentList + products)
    }

    private fun handlDataChangeCart(position: Int?) {
        position?.let {
            getViewDataBinding().content.emptyRecycle.adapter?.notifyItemChanged(position)
        }
    }


}