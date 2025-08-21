package com.example.basekotlin.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import com.example.basekotlin.service.StatusAccessibilityService
import androidx.core.net.toUri

object PermissionManager {

    fun checkFullPermission(context: Context): Boolean {
        return isAccessibilityServiceEnabled(context)
    }

    fun isAccessibilityServiceEnabled(context: Context): Boolean {
        val expectedComponentName =
            ComponentName(context, StatusAccessibilityService::class.java.name)
        val enabledServices = Settings.Secure.getString(
            context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServices)
        while (colonSplitter.hasNext()) {
            val componentName = colonSplitter.next()
            if (ComponentName.unflattenFromString(componentName) == expectedComponentName) {
                return true
            }
        }

        return false
    }

    fun enableAccessibility(context: Context): Intent {
        var intent = Intent("com.samsung.accessibility.installed_service")
        if (intent.resolveActivity(context.packageManager) == null) {
            intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
        }

        val bundle = Bundle().apply {
            putString(
                ":settings:fragment_args_key",
                "${context.packageName}/${StatusAccessibilityService::class.java.name}"
            )
        }

        intent.putExtra(
            ":settings:fragment_args_key",
            "${context.packageName}/${StatusAccessibilityService::class.java.name}"
        )
        intent.putExtra(":settings:show_fragment_args", bundle)
        return intent
    }

    fun checkOverlayPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun requestOverlayPermission(context: Context) {
        val intent = Intent(
            android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        if (context is android.app.Activity) {
            context.startActivity(intent)
        }
    }

}