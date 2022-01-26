package com.example.mvvm_template.ui.component.card_categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.AbstractViewHolder
import com.example.mvvm_template.databinding.ItemLayoutProductBinding
import com.example.mvvm_template.databinding.ItemProductLayoutRowBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.loadImage

class ProductListCategoryAdapter(val clickItem:(Product)->Unit,
                                 val clickAddCart:(Product,Int)->Unit,
                                 val clickBuy:(Product)->Unit
                             ):
    ListAdapter<Product,ProductListCategoryAdapter.SingleRow>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_layout_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(getItem(p1),p1)
        p0.binding.buyNow.setOnClickListener { clickBuy(getItem(p1)) }
        p0.binding.root.setOnClickListener {
            clickItem(getItem(p1))
        }
        p0.binding.addCart.setOnClickListener {
            clickAddCart(getItem(p1),p1)
        }
    }

    class SingleRow(var binding: ItemProductLayoutRowBinding) :
        AbstractViewHolder<Product>(binding.root) {
        override fun bind(item: Product, position: Int) {
            binding.name.text=item.name
            binding.tvOldPrice.setText("${item.price} ${item.currency?.symbol}")
            binding.price.setText("${item.priceAfterDiscount ?: ""} ${item.currency?.symbol ?:""}")
            binding.metaDesc.text = item.metaDescription ?: ""
            binding.image.loadImage(item.imagePath,R.drawable.bg_no_image)

            if (item.isAtCart){
                binding.addCart.setTextColor(binding.addCart.context.resources.getColor(R.color.white))
                binding.addCart.background=binding.addCart.context.resources.getDrawable(R.drawable.back_strok_solid_color_accent)
            }else{
                binding.addCart.setTextColor(binding.addCart.context.resources.getColor(R.color.colorAccent))
                binding.addCart.background=binding.addCart.context.resources.getDrawable(R.drawable.back_strok_color_accent)
            }
        }
    }


    private class CategoryDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id
    }

}