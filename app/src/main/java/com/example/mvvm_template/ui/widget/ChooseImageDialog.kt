package com.smart_zone.mnasati.ui.common.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mvvm_template.R


class ChooseImageDialog(context: Context) : Dialog(context) {


    lateinit var cameraTxt: TextView
    lateinit var galleryTxt: TextView
    var clickCamera: (() -> Unit)? = null
    var clickGallery: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_choose_image)
        val window = window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        galleryTxt = findViewById(R.id.gallery)
        cameraTxt = findViewById(R.id.camera)
        window.attributes.windowAnimations = R.style.PopupAnimation
        val width = (context.resources.displayMetrics.widthPixels * 1.0).toInt()
        getWindow()!!.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setGravity(Gravity.BOTTOM)
        val wlp = window!!.attributes
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        setCancelable(true)
        cameraTxt.setOnClickListener {
            clickCamera?.invoke()
            dismiss()
            clickCamera=null
        }
        galleryTxt.setOnClickListener {
            clickGallery?.invoke()
            dismiss()
            clickGallery=null
        }
    }

    fun show(clickCamera: (() -> Unit)?, clickGallery: (() -> Unit)?) {
        show()
        this.clickCamera = clickCamera
        this.clickGallery = clickGallery
    }

}