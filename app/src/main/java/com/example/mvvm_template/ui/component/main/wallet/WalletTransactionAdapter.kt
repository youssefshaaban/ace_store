package com.example.mvvm_template.ui.component.main.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BUY_PRODUCT
import com.example.mvvm_template.data.remote_service.response.customer.TransactionPoints
import com.example.mvvm_template.data.remote_service.response.customer.TransactionWallet
import com.example.mvvm_template.databinding.ItemTransactionWalletBinding
import com.example.mvvm_template.utils.DateAndTimeFormateUtil

class WalletTransactionAdapter(val list: List<TransactionWallet>) :
    RecyclerView.Adapter<WalletTransactionAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_transaction_wallet,
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

    inner class SingleRow(var binding: ItemTransactionWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = list[pos]
            binding.trans=item
            if (item.action== BUY_PRODUCT){
                binding.description.text=binding.root.context.getString(R.string.txt_buy_product,item.amount.toString()) + item.currency.name
            }else{
                binding.description.text=binding.root.context.getString(R.string.txt_charge_visa_product,item.amount.toString()) + item.currency.name
            }
            binding.date.text = DateAndTimeFormateUtil.parseDateFormate(
                item.actionDate.split("T").firstOrNull(),
                DateAndTimeFormateUtil.yyyy_MM_ddFORMATE,
                "yyyy MMM dd"
            )
        }


    }


}