package com.example.mvvm_template.ui.component.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivitySettingBinding
import com.example.mvvm_template.domain.dto.RequestUpdateSetting
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    val viewModel: SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = getString(R.string.account_setting)
        setObservable()
        getViewDataBinding().save.setOnClickListener {
            viewModel.saveSetting(
                requestUpdateSetting = RequestUpdateSetting(
                    sendCardCodeByEmail = getViewDataBinding().switchEmail.isChecked,
                    sendCardCodeByMobileNumber = getViewDataBinding().switchCode.isChecked
                )
            )
        }
        getViewDataBinding().cancel.setOnClickListener {
            onBackPressed()
        }

        SavePrefs(this, RequestUpdateSetting::class.java).load()?.let {
            getViewDataBinding().switchCode.isChecked = it.sendCardCodeByMobileNumber ?: false
            getViewDataBinding().switchEmail.isChecked = it.sendCardCodeByEmail ?: false
        }

    }

    private fun setObservable() {
        observe(viewModel.successLiveData) {
            SavePrefs(this, RequestUpdateSetting::class.java).save(it)
            onBackPressed()
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

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }
}