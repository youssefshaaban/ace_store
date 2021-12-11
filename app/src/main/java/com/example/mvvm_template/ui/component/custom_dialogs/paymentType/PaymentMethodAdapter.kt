package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemLayoutProductBinding
import com.example.mvvm_template.databinding.ItemLayoutProductCartBinding
import com.example.mvvm_template.databinding.ItemMethodTypeBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.loadImage

class PaymentMethodAdapter(val cartProducts: List<PaymentMethod>,val clickPayment:(PaymentMethod)->Unit
                             ) : RecyclerView.Adapter<PaymentMethodAdapter.SingleRow>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_method_type,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemMethodTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item=cartProducts[pos]
            binding.content.setOnClickListener { clickPayment(item) }
        }
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }


}