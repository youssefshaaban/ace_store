package com.example.mvvm_template.ui.component.custom_dialogs.order_detail

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemChildProductDetailLayoutBinding
import com.example.mvvm_template.databinding.ItemOrderDetailLayoutBinding
import com.example.mvvm_template.domain.entity.OrderDetail
import com.wanjian.view.ExpandableAdapter



class OrderItemDetailAdapter(val list: List<OrderDetail>) :
    ExpandableAdapter<OrderItemDetailAdapter.SingleRow, OrderItemDetailAdapter.SingleRowChild>() {


    inner class SingleRow(var binding: ItemOrderDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            val item = list[pos]
            binding.txtProductName.text = item.productName
            binding.number.text =
                "${binding.number.context.getString(R.string.number)} : ${item.quantity}"
            binding.price.text =
                "${item.totalPrice} ${binding.price.context.getString(R.string.currencyKSA)}"

        }


    }

    inner class SingleRowChild(var binding: ItemChildProductDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int,childPos:Int) {
            val item = list[pos].codes?.get(childPos)
            binding.code.text = item
            binding.copy.setOnClickListener {
                val cm: ClipboardManager = binding.code.context
                    .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.setText(binding.code.text.toString())
                Toast.makeText(
                    binding.code.context,
                    "تم النسخ :)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    override fun getGroupCount(): Int {
        return list.size
    }

    override fun getChildCount(groupIndex: Int): Int {
        return list[groupIndex].codes?.size ?: 0
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.item_order_detail_layout,
                parent,
                false
            )
        )
    }

    override fun onBindGroupViewHolder(p0: SingleRow?, p1: Int) {
        p0?.bind(p1)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): SingleRowChild {
        return SingleRowChild(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.item_child_product_detail_layout,
                parent,
                false
            )
        )
    }

    override fun onBindChildViewHolder(p0: SingleRowChild?, p1: Int, p2: Int) {
        p0?.bind(p1,p2)
    }


}