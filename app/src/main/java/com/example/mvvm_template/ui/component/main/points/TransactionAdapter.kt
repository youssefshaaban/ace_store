package com.example.mvvm_template.ui.component.main.points

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.data.remote_service.response.customer.TransactionPoints
import com.example.mvvm_template.databinding.ItemLayoutPointsBinding

class TransactionAdapter(val list: List<TransactionPoints>) :
    RecyclerView.Adapter<TransactionAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_points,
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

    inner class SingleRow(var binding: ItemLayoutPointsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = list[pos]
            binding.title.text=item.pointsGainMethod
            binding.pointValue.text=item.points.toString()
        }


    }


}