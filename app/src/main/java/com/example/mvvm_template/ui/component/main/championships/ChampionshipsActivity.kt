package com.example.mvvm_template.ui.component.main.championships

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityChampionshipsBinding
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.utils.*
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionshipsActivity : BaseActivity<ActivityChampionshipsBinding>() {
    val championsshipAdapter: ChampionsshipAdapter by lazy {
        ChampionsshipAdapter(::handleChallange)
    }

    val viewModel: ChallengeViewModel by viewModels()
    var pagNumber: Int = FIRST_PAGE
    var status: Int = 1
    private fun handleChallange(challenge: Challenge) {
        startActivityWithFade(ChallengeDetailActivity.getIntent(this)
            .putExtra("title",challenge.name)
            .putExtra("id",challenge.id))
    }

    private fun handleLoadMore() {
        if (!viewModel.stopLoadingMore) {
            pagNumber += 1
            viewModel.getChallenges(pagNumber, status)
        }
    }

    private fun fetchData() {
        viewModel.getChallenges(pagNumber, status)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = getString(R.string.menu_championships)
        getViewDataBinding().rvChampionships.configRecycle(true)
        getViewDataBinding().rvChampionships.addEndlessScroll(::handleLoadMore)
        getViewDataBinding().rvChampionships.adapter = championsshipAdapter
        stUpTaps()
        setUpObservable()
        fetchData()
        getViewDataBinding().tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                status = tab?.position!! + 1
                pagNumber = FIRST_PAGE
                fetchData()
                championsshipAdapter.submitList(null)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpObservable() {
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else
                hideLoading()
        }

        observe(viewModel.faluireLiveData, ::handleFaluir)
        observe(viewModel.challengesLiveData) {
            championsshipAdapter.submitList(championsshipAdapter.currentList + it)
        }
    }

    private fun stUpTaps() {
        getViewDataBinding().tabs.addTab(
            getViewDataBinding().tabs.newTab().setText(getString(R.string.live))
        )
        getViewDataBinding().tabs.addTab(
            getViewDataBinding().tabs.newTab().setText(getString(R.string.next_text))
        )
        getViewDataBinding().tabs.addTab(
            getViewDataBinding().tabs.newTab().setText(getString(R.string.previous_text))
        )
//        getViewDataBinding().tabs.addTab(
//            getViewDataBinding().tabs.newTab().setText(getString(R.string.all_text))
//        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_championships
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, ChampionshipsActivity::class.java)
    }
}