package com.example.basekotlin.service

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.basekotlin.R
import com.example.basekotlin.ui.main.MainActivity
import kotlin.math.abs

class FloatingButtonService : Service() {

    private var windowManager: WindowManager? = null
    private var floatingButton: View? = null
    private var params: WindowManager.LayoutParams? = null
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var isDragging = false

    override fun onCreate() {
        super.onCreate()
        createFloatingButton()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createFloatingButton() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        floatingButton = LayoutInflater.from(this).inflate(R.layout.floating_button_layout, null)

        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.logo_app)
            background = ContextCompat.getDrawable(this@FloatingButtonService, R.drawable.floating_button_background)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        floatingButton = imageView

        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            120,
            120,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 100
        }

        floatingButton?.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params?.x ?: 0
                    initialY = params?.y ?: 0
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false

                    animateScale(view, 0.9f)
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    if (abs(deltaX) > 10 || abs(deltaY) > 10) {
                        isDragging = true
                    }

                    if (isDragging) {
                        params?.x = initialX + deltaX.toInt()
                        params?.y = initialY + deltaY.toInt()
                        windowManager?.updateViewLayout(floatingButton, params)
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    animateScale(view, 1.0f)

                    if (!isDragging) {
                        onFloatingButtonClick()
                    } else {
                        snapToEdge()
                    }

                    isDragging = false
                    true
                }

                else -> false
            }
        }

        try {
            windowManager?.addView(floatingButton, params)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun animateScale(view: View, scale: Float) {
        ObjectAnimator.ofFloat(view, "scaleX", scale).apply {
            duration = 100
            start()
        }
        ObjectAnimator.ofFloat(view, "scaleY", scale).apply {
            duration = 100
            start()
        }
    }

    private fun snapToEdge() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val currentX = params?.x ?: 0

        val targetX = if (currentX < screenWidth / 2) {
            20
        } else {
            screenWidth - 140
        }

        val animator = ObjectAnimator.ofInt(currentX, targetX).apply {
            duration = 300
            addUpdateListener { animation ->
                params?.x = animation.animatedValue as Int
                windowManager?.updateViewLayout(floatingButton, params)
            }
        }
        animator.start()
    }

    private fun onFloatingButtonClick() {
        showFloatingMenu()
    }

    private fun showFloatingMenu() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingButton?.let { windowManager?.removeView(it) }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        fun startService(context: Context) {
            val intent = Intent(context, FloatingButtonService::class.java)
            context.startService(intent)
        }

        fun stopService(context: Context) {
            val intent = Intent(context, FloatingButtonService::class.java)
            context.stopService(intent)
        }
    }
}