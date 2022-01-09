package com.example.mvvm_template.ui.component.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemLayoutProductBinding
import com.example.mvvm_template.databinding.ItemLayoutProductCartBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.loadImage

class CartAdapter(val cartProducts: List<Product>,val clickAddCart:(Int,Int)->Unit
                             ) : RecyclerView.Adapter<CartAdapter.SingleRow>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_product_cart,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemLayoutProductCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item=cartProducts[pos]
            binding.name.text=item.name
            binding.tvOldPrice.setText("${item.price} ${item.currency?.symbol}")
            binding.price.setText("${item.priceAfterDiscount ?: ""} ${item.currency?.symbol ?:""}")
            binding.value.setText(item.quantity.toString())
            binding.image.loadImage(item.imagePath,R.drawable.bg_no_image)
            binding.plus.setOnClickListener {
                clickAddCart(item.id,item.quantity+1)
            }
            binding.delete.setOnClickListener {
                clickAddCart(item.id,0)
            }
            binding.minus.setOnClickListener {
                clickAddCart(item.id,item.quantity-1)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }


}