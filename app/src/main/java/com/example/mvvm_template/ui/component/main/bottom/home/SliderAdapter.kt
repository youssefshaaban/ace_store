package com.example.mvvm_template.ui.component.main.bottom.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemSlideLayoutBinding
import com.example.mvvm_template.domain.entity.Slider
import com.example.mvvm_template.utils.loadImage

class SliderAdapter(val sliders: List<Slider>, val clickItem:(Slider)->Unit) :
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
        return sliders.size
    }

    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemSlideLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item=sliders[pos]
            binding.image.loadImage(item.imagePath,R.drawable.bg_no_image)
            binding.root.setOnClickListener { clickItem(item) }
        }


    }


}