package com.example.mvvm_template.ui.component.main.bottom.offer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemLayoutCardBinding
import com.example.mvvm_template.domain.entity.Card

class CardAdapter(val list: List<Card>?=null) :
    RecyclerView.Adapter<CardAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_card,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemLayoutCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {


        }


    }


}