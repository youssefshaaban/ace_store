package com.example.mvvm_template.ui.component.card_categories

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.CART_COUNT
import com.example.mvvm_template.core.common.CART_UPDATE
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityItemCategoriesBinding
import com.example.mvvm_template.domain.entity.POINTS_PAYMENT_METHOD_TYPE
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.entity.WALLET_PAYMENT_METHOD_TYPE
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.ui.component.payment.CHARGE_WALLET
import com.example.mvvm_template.ui.component.payment.PLACE_ORDER
import com.example.mvvm_template.ui.component.payment.PaymentActivity

import com.example.mvvm_template.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductCategoriesFragment : BaseFragment<ActivityItemCategoriesBinding>() {
    var pagNumber: Int = FIRST_PAGE
    val viewModel: ProductsViewModel by activityViewModels()
    val cat_id: Int? by lazy {
        arguments?.getInt("cat_id")
    }
    var search: String? = null
    var selectProduct: Product? = null
    var isBuyNow = false

    @Inject
    lateinit var appNavigator: AppNavigator
    private val productCategoryAdapter by lazy {
        ProductCategoryAdapter(::handleClickProduct, ::handleAddToCart, ::handleClickBuy)
    }

    private fun handleAddToCart(product: Product, position: Int) {

        if (product.productType == 1) {
            viewModel.addToCart(product.id, position, isAddCart = !product.isAtCart)
        } else if (product.productType == 2) {
            // show dialog player id
            showDialog(product, position)
        }
    }

    private fun showDialog(product: Product, position: Int?=null) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.set_your_amount))

// Set up the input
        val input = EditText(requireContext())
        input.gravity = Gravity.CENTER
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(getString(R.string.amount))
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which ->
            // Here you get get input text from the Edittext
            val player_id = input.text.toString()
            if (!player_id.isNullOrEmpty()) {
                viewModel.addToCart(
                    product.id,
                    position,
                    playerId = player_id,
                    isAddCart = !product.isAtCart
                )
                dialog.dismiss()
            } else {
                getViewDataBinding().grid.showToast(
                    getString(R.string.message_should_enter_player_id),
                    1000
                )
            }
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }


    private fun handleClickBuy(product: Product) {
        isBuyNow=true
        selectProduct = product
        if (product.productType == 2) {
            viewModel.addToCart(product.id, isAddCart = !product.isAtCart)
        } else if (product.productType == 1) {
            // show dialog player id
            showDialog(product)
        }
    }


    private fun handleClickProduct(product: Product) {
        appNavigator.navigateTo(Screen.PRODUCT_DETAIL, Bundle().apply {
            putInt("ID", product.id)
            putBoolean("isAddCart", product.isAtCart)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionGrid()
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        getViewDataBinding().content.emptyRecycle.adapter = productCategoryAdapter
        getViewDataBinding().lastOrder.toGone()
        onClickListner()
        if (arguments?.getBoolean(
                "isSearch",
                false
            ) == null || requireArguments().getBoolean("isSearch", false)
        ) {
            //getViewDataBinding().lastOrder.toGone()
        } else {
            // getViewDataBinding().lastOrder.toVisible()
            viewModel.getProducts(cat_id, pageIndex = pagNumber)
        }
        getViewDataBinding().content.emptyRecycle.addEndlessScroll(::handleLoadMore)
    }

    private fun handleLoadMore() {
        if (!viewModel.stopLoadingMore) {
            pagNumber += 1
            viewModel.getProducts(cat_id = cat_id, searchText = search, pagNumber)
        }
    }

    private fun onClickListner() {
        getViewDataBinding().list.setOnClickListener {
            actionList()
        }
        getViewDataBinding().grid.setOnClickListener {
            actionGrid()
        }
    }


    private fun actionGrid() {
        getViewDataBinding().grid.setColorFilter(resources.getColor(R.color.colorAccent))
        getViewDataBinding().list.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2, true)
    }


    private fun actionList() {
        getViewDataBinding().list.setColorFilter(resources.getColor(R.color.colorAccent))
        getViewDataBinding().grid.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
    }

    private fun handleDateStatCategory(products: List<Product>) {
        productCategoryAdapter.submitList(productCategoryAdapter.currentList + products)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_item_categories
    }

    override fun observeViewModel() {
        observe(viewModel.cartLiveData) { cart ->
            cart?.let {
                val intent = Intent(CART_UPDATE)
                intent.putExtra(CART_COUNT, cart.products?.sumOf { prod -> prod.quantity })
                LocalBroadcastManager.getInstance(getViewDataBinding().root.context)
                    .sendBroadcast(intent)
                if(isBuyNow){
                    appNavigator.navigateTo(Screen.CART)
                }
            }
        }
        observe(viewModel.productsLiveData, ::handleDateStatCategory)
        observe(viewModel.loadinVisibilityObserve) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        observe(viewModel.notifyPositionCartLiveDate, ::handlDataChangeCart)
        observe(viewModel.searchQueryLiveData) {
            // perform search
            search = it
            pagNumber = FIRST_PAGE
            viewModel.getProducts(searchText = search, pageIndex = pagNumber)
        }
        observe(viewModel.errorFaliureCallingLiveDate) { faluir ->
            faluir?.let {
                handleFaluir(it)
            }
        }
    }

    private fun handlDataChangeCart(position: Int?) {
        position?.let {
            getViewDataBinding().content.emptyRecycle.adapter?.notifyItemChanged(position)
        }
    }

}