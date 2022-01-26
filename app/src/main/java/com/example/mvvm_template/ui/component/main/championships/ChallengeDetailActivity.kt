package com.example.mvvm_template.ui.component.main.championships

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityChallengeDetailBinding
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailActivity : BaseActivity<ActivityChallengeDetailBinding>() {

    val viewModel: ChallengeViewModel by viewModels()
    var challenge: Challenge? = null
    val id: Int by lazy {
        intent.getIntExtra("id", 0)
    }
    val title: String? by lazy {
        intent.getStringExtra("title")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = title
        viewModel.getChallengeById(id)
        setUpobservable()
        getViewDataBinding().join.setOnClickListener {
            DialogJoinChallangelFragment.newInstance().apply {
                arguments=Bundle().apply {
                    putInt("id",this@ChallengeDetailActivity.id)
                }
            }.show(supportFragmentManager,DialogJoinChallangelFragment::class.java.name)
        }
        getViewDataBinding().numberOfParticipant.setOnClickListener {
            challenge?.let { ch ->
                ch.players?.let {
                    startActivityWithFade(
                        PlayerschallengeActivity.getIntent(this).putParcelableArrayListExtra(
                            "players",
                            ArrayList(it)
                        )
                    )
                }
            }
        }
    }

    private fun setUpobservable() {
        observe(viewModel.challengeLiveData) { challenge ->
            if (challenge != null) {
                this.challenge = challenge
                getViewDataBinding().imgLive.loadImage(challenge.imagePath, R.drawable.bg_no_image)
                getViewDataBinding().numberOfParticipant.text
                challenge.description?.let { getViewDataBinding().description.setHtml(it) }
                challenge.instructions?.let { getViewDataBinding().instractionValue.setHtml(it) }
                challenge.rewards?.let { getViewDataBinding().rewardsValue.setHtml(it) }
                if (challenge.status == 2) {
                    getViewDataBinding().join.toVisible()
                } else {
                    getViewDataBinding().join.toGone()
                }
            }
        }
        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, ChallengeDetailActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_challenge_detail
    }
}