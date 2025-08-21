package com.example.basekotlin.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlin.data.listener.TapListener
import com.example.basekotlin.data.listener.TapNoHandleListener
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.util.PermissionManager

fun View.tap(action: (view: View?) -> Unit) {
    setOnClickListener(object : TapListener() {
        override fun onTap(v: View?) {
            action(v)
        }
    })
}

fun View.tapNoHandle(action: (view: View?) -> Unit) {
    setOnClickListener(object : TapNoHandleListener() {
        override fun onTap(v: View?) {
            action(v)
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
fun View.tapDouble(onDoubleClick: () -> Unit) {
    val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                onDoubleClick()
                return true
            }
        })

    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
    }
}

fun SeekBar.onProgressChanged(listener: (progress: Int, fromUser: Boolean) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            listener(progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showState(state: Boolean) {
    if (state) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun ImageView.setSrcImage(drawable: Int) {
    setImageResource(drawable)
}

fun TextView.setTextBackground(context: Context, drawable: Int) {
    background = ContextCompat.getDrawable(context, drawable)
}

fun TextView.setTextColor(color: String) {
    setTextColor(color.toColorInt())
}

fun View.setTintBackgroundView(color: String) {
    backgroundTintList = ColorStateList.valueOf(color.toColorInt())
}

fun View.setBackground(drawable: Int) {
    setBackgroundResource(drawable)
}

fun RecyclerView.onScrollDirection(
    onScrollUp: () -> Unit = {},
    onScrollDown: () -> Unit = {}
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            when {
                dy > 0 -> onScrollDown()
                dy < 0 -> onScrollUp()
            }
        }
    })
}

fun View.setTintStatus(type: EnumStatusColor, activity: Activity) {
    backgroundTintList = if (PermissionManager.checkFullPermission(activity)) {
        when (type) {
            EnumStatusColor.WHITE -> {
                ColorStateList.valueOf("#FFFFFF".toColorInt())
            }

            EnumStatusColor.BACKGROUND -> {
                ColorStateList.valueOf("#F8F8F8".toColorInt())
            }
        }
    } else {
        ColorStateList.valueOf("#000000".toColorInt())
    }
}
