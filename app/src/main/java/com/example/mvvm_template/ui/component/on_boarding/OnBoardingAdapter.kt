package com.example.mvvm_template.ui.component.on_boarding
import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_template.R
import com.smart_zone.mnasati.R
import com.smart_zone.mnasati.databinding.ItemOnBoardingBinding
import com.smart_zone.mnasati.utils.LogUtil


class OnBoardingAdapter(val context: Context) : RecyclerView.Adapter<OnBoardingAdapter.SingleRow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRow {
        return SingleRow(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_on_boarding,
                        parent,
                        false
                )
        )
    }


    override fun getItemCount(): Int {
        return 3
    }
    override fun onBindViewHolder(p0: SingleRow, p1: Int) {
        p0.bind(p1)
    }

    inner class SingleRow(var binding: ItemOnBoardingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int){
            if (pos==0){
               // binding.title.text = context.resources.getString(R.string.)
                   binding.title.text = "Lorem ipsum dolor sit amet"
                binding.description.text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea"
                binding.image.setImageResource(R.drawable.ic_board_1)


            }else if (pos==1){
                binding.title.text = "Lorem ipsum dolor sit amet"
                binding.description.text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea"
                binding.image.setImageResource(R.drawable.ic_board_2)


            }else if (pos==2){

                binding.title.text = "Lorem ipsum dolor sit amet"
                binding.description.text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea"
                binding.image.setImageResource(R.drawable.ic_board_3)

            }
        }
    }



}