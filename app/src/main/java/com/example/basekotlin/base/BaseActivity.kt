package com.example.basekotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.basekotlin.R
import com.example.basekotlin.ads.IsNetWork
import com.example.basekotlin.data.call_api.EmojiDataRepository
import com.example.basekotlin.data.call_api.EmojiDataVM
import com.example.basekotlin.data.call_api.EmojiDataVMFactory
import com.example.basekotlin.ui.no_internet.NoInternetActivity
import com.example.basekotlin.ui.splash.SplashActivity
import com.example.basekotlin.ui.welcome_back.WelcomeBackActivity
import com.example.basekotlin.util.SystemUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    protected val binding: VB by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        SystemUtil.setLocale(this)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        window.hideNavigation(binding.root)

        getData()
        initView()
        bindView()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })

        if (this !is SplashActivity) {
            setupCommonObservers()
        }

        viewModel.checkNetWork(this)
        viewModel.isNetWork.observe(this) { isNetWork ->
            if (!isNetWork && this !is SplashActivity && this !is NoInternetActivity) {
                startNextActivity(NoInternetActivity::class.java, null)
            }
        }
    }

    protected val viewModel: EmojiDataVM by lazy {
        val repository = EmojiDataRepository()
        val factory = EmojiDataVMFactory(repository)
        ViewModelProvider(this, factory)[EmojiDataVM::class.java]
    }

    private fun setupCommonObservers() {
        viewModel.isLoading.observe(this) { isLoading ->
            handleLoadingState(isLoading)
        }

        viewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
//                showToastByString(error)
            }
        }
    }

    protected open fun handleLoadingState(isLoading: Boolean) {
        // Default loading handling - có thể override trong activity con
    }

    open fun getData() {
    }

    open fun initView() {

    }

    open fun bindView() {

    }

    open fun reloadAds() {

    }


    fun startNextActivity(activity: Class<*>?, bundle: Bundle?) {
        var bundle = bundle
        val intent = Intent(this, activity)
        if (bundle == null) {
            bundle = Bundle()
        }
        intent.putExtras(bundle)
        resultLauncher.launch(intent)
        overridePendingTransition(R.anim.in_right, R.anim.out_left)
    }

    private var resultLauncher =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                reloadAds()
            }
        }

    fun finishThisActivity() {
        finish()
        overridePendingTransition(R.anim.in_left, R.anim.out_right)
    }

    open fun onBack() {
        setResult(RESULT_OK)
        finishThisActivity()
    }
}