package com.example.mvvm_template.ui.component.auth.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.utils.loadImage

class CountryCodeSpinnerAdapter(context: Context, val resourceID:Int,val  countries: List<Country>): ArrayAdapter<Country>(context,resourceID,countries) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,parent)
    }

    private fun getCustomView(position: Int, parent: ViewGroup): View {
        val row: View = LayoutInflater
            .from(parent.context).inflate(resourceID, parent, false)
        val countryCode = row.findViewById<TextView>(R.id.code)
        countryCode.setText(countries.get(position).countryCode)
        val countryFlag = row.findViewById<ImageView>(R.id.flage)
        countryFlag.loadImage(countries.get(position).imagePath,R.drawable.bg_no_image)
        // countryFlag.setImageResource(countries.get(position).getCountryFlag());
        return row
    }
}