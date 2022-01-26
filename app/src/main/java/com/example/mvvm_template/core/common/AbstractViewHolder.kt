package com.example.mvvm_template.core.common

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Raed Saeed on 28/01/2021
 **/

abstract class AbstractViewHolder<T>(@NonNull itemView: View?) :
    RecyclerView.ViewHolder(itemView!!) {
    abstract fun bind(item: T,position:Int)
}