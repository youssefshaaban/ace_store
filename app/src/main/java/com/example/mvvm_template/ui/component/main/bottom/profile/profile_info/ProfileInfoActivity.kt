package com.example.mvvm_template.ui.component.main.bottom.profile.profile_info

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import codes.ahmednts.vivantor.filepicker.VFilePicker
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.ActivityProfileInfoBinding
import com.example.mvvm_template.domain.dto.FieldsFormValidation
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.utils.FileUtils2
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import com.smart_zone.mnasati.ui.common.dialogs.ChooseImageDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileInfoActivity : BaseActivity<ActivityProfileInfoBinding>() {
    lateinit var filePicker: VFilePicker
    val viewModel: ProfileInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().cancel.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = getString(R.string.account_info)
        onButtonClicked()
        setupObservable()
        viewModel.getProfile()
    }

    private fun setupObservable() {
        observe(viewModel.pathesLiveData, ::handleListState)
        observe(viewModel.observeProfile, ::handleProfileState)
        observe(viewModel.validationExceptionLiveDate,::handleValidation)
    }

    private fun handleProfileState(dataState: DataState<Profile>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                getViewDataBinding().email.setText(dataState.data.email ?: "")
                getViewDataBinding().name.setText(dataState.data.name ?: "")
                getViewDataBinding().profile.loadImage(dataState.data.imagePath,R.drawable.bg_no_image)
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }


    private fun handleValidation(input: Int) {
        when (input) {
            FieldsFormValidation.EMPTY_EMAIL.value -> displayError(getString(R.string.validate_must_enter_email))
            FieldsFormValidation.INVALID_EMAIL.value -> displayError(getString(R.string.invalide_enter_name))
            FieldsFormValidation.EMPTY_FIRSTNAME.value -> displayError(getString(R.string.validate_must_enter_usern))
            FieldsFormValidation.EMPTY_LASTNAME.value -> displayError(getString(R.string.validate_must_enter_second_name))
        }
    }

    private fun handleListState(dataState: DataState<List<String>>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                viewModel.pathImage=dataState.data.firstOrNull()
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_profile_info
    }


    fun handleClickGallery() {
        filePicker = VFilePicker()
            .pick(VFilePicker.IMAGE)
            .from(VFilePicker.GALLERY)
        filePicker.show(this)
    }


    private fun onButtonClicked() {
        getViewDataBinding().upload.setOnClickListener {
            pickupMediaFile()
        }
        getViewDataBinding().save.setOnClickListener { viewModel.updateProfile(
            getViewDataBinding().email.text.toString(),
            getViewDataBinding().name.text.toString(),
            getViewDataBinding().secondName.text.toString()
        ) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val fileInfo = filePicker.onActivityResult(this, requestCode, resultCode, data)
            val path = FileUtils2.compressImage(fileInfo.filePath)
            viewModel.uploadFile(File(path))
            getViewDataBinding().profile.loadImage(
                Uri.fromFile(File(fileInfo.filePath)),
                R.drawable.bg_no_image
            )
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePicker.onRequestPermissions(this, requestCode, permissions, grantResults)
    }

    fun pickupMediaFile() {
        ChooseImageDialog(getViewDataBinding().root.context).show(
            clickCamera = ::handleClickCamera,
            clickGallery = ::handleClickGallery
        )
    }

    fun handleClickCamera() {
        filePicker = VFilePicker()
            .pick(VFilePicker.IMAGE)
            .from(VFilePicker.CAMERA)
            .saveTo(getString(R.string.app_name))
        filePicker.show(this)
    }
}