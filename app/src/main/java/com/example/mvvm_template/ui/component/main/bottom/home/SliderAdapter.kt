package com.example.mvvm_template.ui.component.main.bottom.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.data.pojo.Category
import com.example.mvvm_template.databinding.ItemCategoryLayoutBinding
import com.example.mvvm_template.databinding.ItemRvLayoutBinding
import com.example.mvvm_template.databinding.ItemSlideLayoutBinding
import com.example.mvvm_template.ui.component.main.pojo.ActionType
import com.example.mvvm_template.ui.component.main.pojo.MenuItem

class SliderAdapter(val list: List<Category>?=null, val clickItem:(Category)->Unit) :
    RecyclerView.Adapter<SliderAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_slide_layout,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemSlideLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {



        }


    }


}