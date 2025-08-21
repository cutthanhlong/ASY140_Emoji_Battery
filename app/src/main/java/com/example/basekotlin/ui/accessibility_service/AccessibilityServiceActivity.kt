package com.example.basekotlin.ui.accessibility_service

import androidx.activity.result.contract.ActivityResultContracts
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityAccessibilityServiceBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.util.PermissionManager

class AccessibilityServiceActivity :
    BaseActivity<ActivityAccessibilityServiceBinding>(ActivityAccessibilityServiceBinding::inflate) {

    override fun bindView() {
        super.bindView()
        binding.tvGoSetting.tap {
            startToMain.launch(PermissionManager.enableAccessibility(this))
        }
    }

    private var startToMain =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (PermissionManager.checkFullPermission(this)) {
                finish()
            }
        }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}