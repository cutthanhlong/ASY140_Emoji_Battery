package com.example.basekotlin.data.listener

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs


open class OnSwipeTouchListener(context: Context) : View.OnTouchListener {

    private val gestureDetector = GestureDetector(context, GestureListener())

    open fun onMyLongPress() {}
    open fun onSingleTap() {}
    open fun onSwipeBottom() {}
    open fun onSwipeLeft() {}
    open fun onSwipeRight() {}
    open fun onSwipeTop() {}

    companion object {
        private const val SWIPE_THRESHOLD = 30
        private const val SWIPE_VELOCITY_THRESHOLD = 30
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(motionEvent)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(motionEvent: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            onSingleTap()
            return super.onSingleTapUp(motionEvent)
        }

        override fun onLongPress(motionEvent: MotionEvent) {
            onMyLongPress()
            super.onLongPress(motionEvent)
        }

        override fun onFling(
            motionEvent: MotionEvent?,
            motionEvent2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return try {
                val diffY = motionEvent2.y - (motionEvent?.y ?: 0f)
                val diffX = motionEvent2.x - (motionEvent?.x ?: 0f)
                when {
                    abs(diffX) > abs(diffY) -> {
                        if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) onSwipeRight() else onSwipeLeft()
                        } else {
                            return false
                        }
                    }

                    else -> {
                        if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) onSwipeBottom() else onSwipeTop()
                        } else {
                            return false
                        }
                    }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
