package com.example.mvvm_template.ui.component.main.member_ship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.data.remote_service.response.customer.TransactionPoints
import com.example.mvvm_template.databinding.ItemLayoutPointsBinding
import com.example.mvvm_template.domain.entity.MemberType

class MemberTypeAdapter(val list: List<MemberType>) :
    RecyclerView.Adapter<MemberTypeAdapter.SingleRow>() {

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
            binding.title.text=item.name
            binding.iconPoint.setImageResource(R.drawable.ic_member)
            binding.pointValue.text=item.minimumPoints.toString()
        }


    }


}