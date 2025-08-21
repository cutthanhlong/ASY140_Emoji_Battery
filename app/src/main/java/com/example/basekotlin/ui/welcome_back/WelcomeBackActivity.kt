package com.example.basekotlin.ui.welcome_back

import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityWelcomeBackBinding
import com.example.basekotlin.service.StatusAccessibilityService
import com.example.basekotlin.util.PermissionManager

class WelcomeBackActivity :
    BaseActivity<ActivityWelcomeBackBinding>(ActivityWelcomeBackBinding::inflate) {

    override fun initView() {
        super.initView()

        if (PermissionManager.checkFullPermission(this)) {
            StatusAccessibilityService.instance?.actionPresent()
        }
    }

    override fun bindView() {
        super.bindView()

        binding.btnStart.tap {
            setResult(2572)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.showState(PermissionManager.checkFullPermission(this))
    }

}