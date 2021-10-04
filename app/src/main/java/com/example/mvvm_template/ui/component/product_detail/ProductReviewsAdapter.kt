package com.example.mvvm_template.ui.component.product_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemCategoryLayoutBinding
import com.example.mvvm_template.databinding.ItemLayoutProductBinding
import com.example.mvvm_template.databinding.ItemReviewLayoutBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.entity.Review
import com.example.mvvm_template.utils.loadImage

class ProductReviewsAdapter :
    ListAdapter<Review,ProductReviewsAdapter.SingleRow>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_review_layout,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemReviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item=getItem(pos)
            binding.customerName.text=item.customerName ?: ""
            binding.date.text=item.dateAdd ?: ""
            binding.ratingBar.rating=item.grade?.toFloat() ?: 0.0f
            binding.review.text=item.customerReview ?: ""

        }
    }


    private class CategoryDiffCallBack : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem.customerId == newItem.customerId

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem.customerId == newItem.customerId
    }

}