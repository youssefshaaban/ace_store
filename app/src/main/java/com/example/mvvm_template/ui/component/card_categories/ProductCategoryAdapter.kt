package com.example.mvvm_template.ui.component.card_categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemLayoutProductBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.loadImage

class ProductCategoryAdapter(val clickItem:(Product)->Unit) :
    ListAdapter<Product,ProductCategoryAdapter.SingleRow>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_product,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemLayoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item=getItem(pos)
            binding.name.text=item.name
            binding.tvOldPrice.setText("${item.price} ${item.currency?.symbol}")
            binding.price.setText("${item.priceAfterDiscount ?: ""} ${item.currency?.symbol ?:""}")
            binding.code.text = item.metaDescription ?: ""
            binding.image.loadImage(item.imagePath,R.drawable.bg_no_image)
            binding.root.setOnClickListener {
                clickItem(item)
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