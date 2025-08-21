package com.example.basekotlin.base

import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.hideNavigationFullNavigation() {
    if (setFullScreenWallpaper()) return

    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        val activityRoot = decorView
        activityRoot.getWindowVisibleDisplayFrame(rect)
        if (setFullScreenWallpaper()) return@addOnGlobalLayoutListener
    }
}

private fun Window.setFullScreenWallpaper(): Boolean {
    val windowInsetsController: WindowInsetsControllerCompat? = if (Build.VERSION.SDK_INT >= 30) {
        ViewCompat.getWindowInsetsController(decorView)
    } else {
        WindowInsetsControllerCompat(this, decorView)
    }

    if (windowInsetsController == null) {
        return true
    }
    setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
    windowInsetsController.hide(WindowInsetsCompat.Type.systemGestures())
    return false
}

fun Window.hideNavigation(root: View) {
    val windowInsetsController = if (Build.VERSION.SDK_INT >= 30) {
        ViewCompat.getWindowInsetsController(decorView)
    } else {
        WindowInsetsControllerCompat(this, root)
    }

    windowInsetsController?.let {
        it.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        it.hide(WindowInsetsCompat.Type.navigationBars())

        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility == 0) {
                Handler().postDelayed({
                    val controller = if (Build.VERSION.SDK_INT >= 30) {
                        ViewCompat.getWindowInsetsController(decorView)
                    } else {
                        WindowInsetsControllerCompat(this, root)
                    }
                    controller?.hide(WindowInsetsCompat.Type.navigationBars())
                }, 3000)
            }
        }
    }
}

