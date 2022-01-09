package com.example.mvvm_template.ui.component.main.championships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.data.remote_service.response.customer.TransactionPoints
import com.example.mvvm_template.databinding.ItemLayoutPointsBinding

class ChampionsshipAdapter(val list: List<TransactionPoints>) :
    RecyclerView.Adapter<ChampionsshipAdapter.SingleRow>() {

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

        }


    }


}