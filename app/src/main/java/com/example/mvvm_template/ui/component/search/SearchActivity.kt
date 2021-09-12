package com.example.mvvm_template.ui.component.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivitySearchBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.ui.component.card_categories.CardCategoriesFragment
import com.example.mvvm_template.ui.component.card_categories.CardViewModel
import com.example.mvvm_template.ui.component.main.rate_app.RateMeActivity
import com.example.mvvm_template.utils.RxSearchObservable.getQueryTextChangeStateFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    val viewModel: CardViewModel by viewModel()
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeSearch()
        getViewDataBinding().back.setOnClickListener { onBackPressed() }

       supportFragmentManager.beginTransaction().replace(R.id.content,CardCategoriesFragment().apply {
           arguments=Bundle().apply {
               putBoolean("isSearch",true)
           }
       },CardCategoriesFragment::class.java.name).commit()

    }


    fun observeSearch() {
        job = scope.launch {
            getViewDataBinding().search.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        getViewDataBinding().search.setText("")
                        return@filter false
                    } else {
                        return@filter true
                    }
                }.distinctUntilChanged().flatMapLatest { query ->
                    flow { emit(query) }
                }.flowOn(Dispatchers.Default).collect {
                    // Toast.makeText(this@SearchActivity, it, Toast.LENGTH_LONG).show()
                    viewModel.setSarchText(it)
                }
        }
    }

    private fun whiteNotificationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags: Int = view.getSystemUiVisibility()
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.setSystemUiVisibility(flags)
            window.statusBarColor = Color.WHITE
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun observeViewModel() {

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object{
        fun getIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
    }
}