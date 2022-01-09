package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import android.annotation.SuppressLint
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
import com.example.mvvm_template.databinding.ItemPaymentMethodNoVisaBinding
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible

class PaymentMethodAdapter(
    val cartProducts: List<PaymentMethod>, val clickPayment: (PaymentMethod) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return SingleRow(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_method_type,
                    parent,
                    false
                )
            )
        } else {
            return SingleRowValuePaymentMethod(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_payment_method_no_visa,
                    parent,
                    false
                )
            )
        }

    }


    override fun getItemViewType(position: Int): Int {
        val item = cartProducts.get(position)
        return if (item.id == 5 || item.id == 6) {
            2
        } else {
            1
        }
    }

    inner class SingleRow(var binding: ItemMethodTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item = cartProducts[pos]
            binding.image.loadImage(item.imagePath, R.drawable.bg_no_image)
            binding.name.text = item.name
            binding.content.setOnClickListener { clickPayment(item) }
        }
    }

    inner class SingleRowValuePaymentMethod(var binding: ItemPaymentMethodNoVisaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            val item = cartProducts[pos]
            if (item.id==6){
                binding.points.toVisible()
                binding.points.text="${binding.root.context.getString(R.string.total_points)} : ${item.totalPoint}"
                binding.walletValue.text="${item.equivalentPointsAmount} ${item.currency?.name}"
            }else{
                binding.points.toGone()
                binding.walletValue.text="${binding.root.context.getString(R.string.menu_wallet)} \n ${item.totalWalletAmount} ${item.currency?.name}"
            }
            binding.content.setOnClickListener { clickPayment(item) }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SingleRow) {
            holder.bind(position)
        } else if (holder is SingleRowValuePaymentMethod) {
            holder.bind(position)
        }
    }


}