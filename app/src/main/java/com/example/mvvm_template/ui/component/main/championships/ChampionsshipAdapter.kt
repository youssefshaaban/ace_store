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
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.utils.DateAndTimeFormateUtil
import com.example.mvvm_template.utils.LogUtil
import com.example.mvvm_template.utils.loadImage
import java.time.Duration
import java.time.LocalDateTime

class ChampionsshipAdapter(val clickListnerJoin: (Challenge) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Challenge, RecyclerView.ViewHolder>(
        ChallengeDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            LIVE -> {
                return SingleRowLive(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_championships_live_layout,
                        parent,
                        false
                    )
                )
            }
            NEXT -> {
                return SingleRowNext(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_championships_next_layout,
                        parent,
                        false
                    )
                )
            }
            PRVIOUS -> {
                return SingleRowPrevious(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_championships_previous_layout,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return SingleRowNext(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_championships_next_layout,
                        parent,
                        false
                    )
                )
            }
        }

    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.status) {
            LIVE -> {
                LIVE
            }
            NEXT -> {
                NEXT
            }
            PRVIOUS -> {
                PRVIOUS
            }
            else -> {
                NEXT
            }
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p0) {
            is SingleRowLive -> {
                p0.bind(p1)
            }
            is SingleRowNext -> {
                p0.bind(p1)
            }
            is SingleRowPrevious -> {
                p0.bind(p1)
            }
        }
    }

    inner class SingleRowLive(var binding: ItemChampionshipsLiveLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = getItem(pos)
            binding.imgLive.loadImage(item.imagePath, R.drawable.ic_championship)
            binding.numberOfParticipant.text = binding.root.context.getString(
                R.string.numberOfChampions,
                item.playersCount.toString()
            )
            binding.root.setOnClickListener { clickListnerJoin(item) }
        }


    }

    inner class SingleRowNext(var binding: ItemChampionshipsNextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = getItem(pos)
            binding.img.loadImage(item.imagePath, R.drawable.ic_championship)
            if (item.startDate != null && item.endDate != null&&item.status==2) {
//                val startDate = DateAndTimeFormateUtil.getDate(
//                    item.startDate,
//                    DateAndTimeFormateUtil.ISO_FORMATE
//                )
//                LogUtil.error("date",startDate?.time.toString())
//
                item.getDays()?.let {
                    binding.firstdigitDay.text="${it}"
                }
                item.getHour()?.let {
                    binding.seconDigitDay.text="${it}"
                }
                item.getMin()?.let {
                    binding.thirdDigithour.text="${it}"
                }
            }
            binding.root.setOnClickListener {
                clickListnerJoin(item)
            }
        }


    }

    inner class SingleRowPrevious(var binding: ItemChampionshipsPreviousLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val item = getItem(pos)
            binding.img.loadImage(item.imagePath, R.drawable.ic_championship)
            binding.title.text = item.name
            binding.winner.text = item.winner?.name
            binding.root.setOnClickListener { clickListnerJoin(item) }

        }


    }

    private class ChallengeDiffCallBack : DiffUtil.ItemCallback<Challenge>() {
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
            oldItem.hashCode().toLong() == newItem.hashCode().toLong()

        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
            oldItem == newItem
    }


}