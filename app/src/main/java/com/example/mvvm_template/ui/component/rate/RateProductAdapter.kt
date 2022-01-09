package com.example.mvvm_template.ui.component.rate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemLayoutProductRateLayoutBinding
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemPurchesdLayoutBinding
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.entity.OrderDetail
import com.example.mvvm_template.utils.DateAndTimeFormateUtil
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible

class RateProductAdapter(
    val list: List<OrderDetail>
) :
    RecyclerView.Adapter<RateProductAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_product_rate_layout,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemLayoutProductRateLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            val item = list[pos]
            binding.nameProdut.text = item.productName
            binding.icon.loadImage(item.imagePath, R.drawable.bg_no_image)
        }


    }


}