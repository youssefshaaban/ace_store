package com.example.mvvm_template.ui.component.custom_dialogs.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemOrderDetailLayoutBinding
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemPurchesdLayoutBinding
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.entity.OrderDetail
import com.example.mvvm_template.utils.DateAndTimeFormateUtil

class OrderItemDetailAdapter(val list: List<OrderDetail>) :
    RecyclerView.Adapter<OrderItemDetailAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_detail_layout,
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

    inner class SingleRow(var binding: ItemOrderDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item=list[pos]
            binding.txtProductName.text=item.productName
            binding.number.text="${binding.number.context.getString(R.string.number)} : ${item.quantity}"
            binding.price.text="${item.totalPrice} ${binding.price.context.getString(R.string.currencyKSA)}"

        }


    }


}