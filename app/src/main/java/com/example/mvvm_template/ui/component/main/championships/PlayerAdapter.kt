package com.example.mvvm_template.ui.component.main.championships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.LIVE
import com.example.mvvm_template.core.common.NEXT
import com.example.mvvm_template.core.common.PRVIOUS
import com.example.mvvm_template.databinding.ItemChampionshipsLiveLayoutBinding
import com.example.mvvm_template.databinding.ItemChampionshipsNextLayoutBinding
import com.example.mvvm_template.databinding.ItemChampionshipsPreviousLayoutBinding
import com.example.mvvm_template.databinding.ItemPlayerLayoutBinding
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.domain.entity.Player
import com.example.mvvm_template.utils.DateAndTimeFormateUtil
import com.example.mvvm_template.utils.loadImage
import java.time.Duration
import java.time.LocalDateTime

class PlayerAdapter() :
    androidx.recyclerview.widget.ListAdapter<Player, PlayerAdapter.SingleRowPlayer>(
        ChallengeDiffCallBack()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerAdapter.SingleRowPlayer {
        return SingleRowPlayer(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_player_layout,
                parent,
                false
            )
        )

    }


    inner class SingleRowPlayer(var binding: ItemPlayerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = getItem(pos)
            binding.profile.loadImage(item.customer?.imagePath, R.drawable.bg_no_image)
            binding.name.text = item.customer?.imagePath
        }


    }


    private class ChallengeDiffCallBack : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem.hashCode().toLong() == newItem.hashCode().toLong()

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: SingleRowPlayer, position: Int) {

    }


}