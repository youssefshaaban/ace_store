package com.example.mvvm_template.ui.component.main.bottom.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemCategoryLayoutBinding
import com.example.mvvm_template.utils.loadImage

class CategoryCardAdapter(val clickItem:(Category)->Unit) :
    ListAdapter<Category,CategoryCardAdapter.SingleRow>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category_layout,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item=getItem(pos)
            binding.name.text=item.name
            binding.image.loadImage(item.imagePath,R.drawable.bg_no_image)
            binding.root.setOnClickListener {
                clickItem(item)
            }
        }
    }


    private class CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id
    }

}