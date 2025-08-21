package com.example.basekotlin.ui.permission

import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityPermissionBinding
import com.example.basekotlin.dialog.permission.PermissionDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.accessibility_service.AccessibilityServiceActivity
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.util.PermissionManager

class PermissionActivity :
    BaseActivity<ActivityPermissionBinding>(ActivityPermissionBinding::inflate) {

    override fun bindView() {
        binding.apply {
            tvContinue.tap { startNextActivity() }

            ivSw.tap {
                if (!PermissionManager.checkFullPermission(this@PermissionActivity)){
                    PermissionDialog(this@PermissionActivity) {
                        startNextActivity(AccessibilityServiceActivity::class.java, null)
                    }.show()
                }
            }

            ivIn4.tap {
                PermissionDialog(this@PermissionActivity) {
                    startNextActivity(AccessibilityServiceActivity::class.java, null)
                }.show()
            }

        }
    }

    private fun startNextActivity() {
        startNextActivity(MainActivity::class.java, null)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        binding.ivSw.setImageResource(if (PermissionManager.checkFullPermission(this)) R.drawable.ic_switch_s else R.drawable.ic_switch_sn)

        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

    override fun onBack() {
        finishAffinity()
    }
}