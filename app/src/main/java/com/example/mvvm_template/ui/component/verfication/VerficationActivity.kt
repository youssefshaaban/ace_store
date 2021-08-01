package com.example.mvvm_template.ui.component.verfication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.ui.component.signup.SignUpActivity

class VerficationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verfication)
    }


    companion object {
        fun getIntent(context: Context): Intent = Intent(context, VerficationActivity::class.java)
    }
}