package com.example.mvvm_template.ui.component.main.bottom.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ItemCardCardsLayoutBinding
import com.example.mvvm_template.databinding.ItemChildLayoutBinding
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.databinding.ItemSlideLayoutBinding
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.utils.loadImage
import com.wanjian.view.ExpandableAdapter

class CardsAdapter(val list: List<Card>, val clickItem:(Category)->Unit) :
    ExpandableAdapter<CardsAdapter.SingleRowGroup,CardsAdapter.SingleRowChild>() {



    inner class SingleRowGroup(var binding: ItemCardCardsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val item= list[pos]
            binding.icon.loadImage(item.iconPath,R.drawable.bg_no_image)
            binding.title.text=item.name
        }
    }




    inner class SingleRowChild(var binding: ItemChildLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupIndex:Int,pos: Int) {
            val str= list[groupIndex].children!![pos]
            binding.name.text=str
        }
    }


    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): SingleRowGroup {
        return SingleRowGroup(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_card_cards_layout,
                parent,
                false
            )
        )
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): SingleRowChild {
        return SingleRowChild(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_child_layout,
                parent,
                false
            )
        )
    }

    override fun getGroupCount(): Int {
        return list.size
    }

    override fun getChildCount(groupIndex: Int): Int {
        return list[groupIndex].children?.size ?: 0
    }

    override fun onBindGroupViewHolder(p0: SingleRowGroup?, p1: Int) {
        p0?.bind(p1)
    }

    override fun onBindChildViewHolder(p0: SingleRowChild?, p1: Int, p2: Int) {
        p0?.bind(p1,p2)
    }


}