package com.example.mvvm_template.ui.component.main.bottom.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemCategoryLayoutBinding
import com.example.mvvm_template.utils.loadImage

class CategoryCardAdapter(val clickItem: (Category) -> Unit) :
    ListAdapter<Category, CategoryCardAdapter.SingleRow>(CategoryDiffCallBack()) {
    private var lastPosition = -1

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
        //setAnimation(p0.itemView,p1)
    }


//    // start animation
//    private fun setAnimation(viewToAnimate: View, position: Int) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            val animation = AnimationUtils.loadAnimation(
//                viewToAnimate.context, R.anim.translate
//            )
//            viewToAnimate.startAnimation(animation)
//            lastPosition = position
//        }
//    }

//    override fun onViewDetachedFromWindow(holder: SingleRow) {
//        super.onViewDetachedFromWindow(holder)
//      //  holder.clearAnimation()
//
//    }

    inner class SingleRow(var binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item = getItem(pos)
            binding.name.text = item.name
            binding.image.loadImage(item.imagePath, R.drawable.bg_no_image)
            binding.root.setOnClickListener {
                clickItem(item)
            }
        }
        fun clearAnimation(){
            binding.root.clearAnimation()
            binding.unbind()
        }
    }


    private class CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.hashCode().toLong() == newItem.hashCode().toLong()
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }

}