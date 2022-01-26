package com.example.mvvm_template.ui.component.main.championships

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityPlayerschallengeBinding
import com.example.mvvm_template.domain.entity.Player

class PlayerschallengeActivity : BaseActivity<ActivityPlayerschallengeBinding>() {
    val listPlayer:ArrayList<Player>? by lazy {
        intent.getParcelableArrayListExtra("players")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_playerschallenge
    }

    companion object{
        fun getIntent(context: Context): Intent =
            Intent(context, PlayerschallengeActivity::class.java)
    }
}