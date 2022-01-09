package com.example.mvvm_template.ui.component.main.bottom.purchase

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemPurchesdLayoutBinding
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.utils.DateAndTimeFormateUtil
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible

class PurchasedCardAdapter(
    val list: List<Order>,
    val clickReorder: (Int) -> Unit,
    val clickItem: (Order) -> Unit
) :
    RecyclerView.Adapter<PurchasedCardAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_purchesd_layout,
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

    inner class SingleRow(var binding: ItemPurchesdLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            val item = list[pos]
            binding.valueOrder.text = item.id.toString()
            binding.date.text = DateAndTimeFormateUtil.parseDateFormate(
                item.orderDate?.split("T")?.firstOrNull(),
                DateAndTimeFormateUtil.yyyy_MM_ddFORMATE,
                "yyyy MMM dd"
            )
            binding.price.text =
                "${item.totalPrice} ${binding.price.context.getString(R.string.currencyKSA)}"
            binding.nameAction.text = item.currentState
            if (item.orderStateId == 2) {
                binding.reorder.toVisible()
            } else binding.reorder.toGone()
            binding.reorder.setOnClickListener { clickReorder(item.id!!) }
            binding.root.setOnClickListener { clickItem(item) }

        }


    }


}