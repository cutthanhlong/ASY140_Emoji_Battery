package com.example.basekotlin.util.customview.battery

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.util.Utils.dpToPx

class BatteryChargingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var width = 0f
    private var height = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()
    }

    private var borderColor = "#7B1400".toColorInt()
    private var contentColor = "#FF4520".toColorInt()
    private var borderSize = 1.dpToPx()
    private var backgroundPin = "#00ffffff".toColorInt()
    private var radius = 3.dpToPx()

    private var percentCharging = 50f
    private var chargeBitmap: Bitmap? = null

    private var indexCharging = 1

    fun setCharging(percent: Float) {
        this.percentCharging = percent
        postInvalidate()
    }

    fun setChargingColor(cl1: String, cl2: String) {
        this.borderColor = cl1.toColorInt()
        this.contentColor = cl2.toColorInt()
        postInvalidate()
    }

    fun setChargingEmoji(index: Int) {
        this.indexCharging = index
        chargingEmoji(index)
        postInvalidate()
    }

    private fun chargingEmoji(index: Int = 1) {
        val drawId = when (index) {
            1 -> R.drawable.ic_charge_2
            2 -> R.drawable.ic_charge_3
            3 -> R.drawable.ic_charge_4
            4 -> R.drawable.ic_charge_5
            5 -> R.drawable.ic_charge_6
            else -> R.drawable.ic_charge_1
        }
        val drawable = ContextCompat.getDrawable(context, drawId) ?: return
        val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        percentageFillRect?.let { rect ->

            val newHeight = (rect.height() * 0.8).toInt()
            val newWidth = (bitmap.width * newHeight) / bitmap.height
            chargeBitmap = bitmap.scale(newWidth, newHeight)
        }
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawItemPin1()
    }

    var percentageFillRect: RectF? = null
    fun Canvas.drawItemPin1() {
        val topBorder = height * 0.1f
        val borderRect = RectF(
            borderSize, topBorder, (width - borderSize), (height - borderSize)
        )
        val topRect = RectF(
            width * 0.2f, 0f, (width * 0.8f), topBorder
        )
        drawRoundRectPath(
            topRect, radius, true, true, false, false, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = borderColor
                isAntiAlias = true
            })

        drawRoundRectPath(
            borderRect, radius, true, true, true, true, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                color = borderColor
                strokeWidth = borderSize
                isAntiAlias = true
            })

        if (percentageFillRect == null) {
            percentageFillRect = RectF(
                (borderRect.left + borderSize * 2),
                borderRect.top + borderSize * 2,
                borderRect.right - borderSize * 2,
                borderRect.bottom - borderSize * 2
            )
            chargingEmoji(indexCharging)
        }

        val fillHeight = percentageFillRect!!.height() * (percentCharging / 100f)
        val percentageRect = RectF(
            percentageFillRect!!.left,
            percentageFillRect!!.bottom - fillHeight,
            percentageFillRect!!.right,
            percentageFillRect!!.bottom
        )

        drawRoundRectPath(
            percentageRect,
            radius * 0.5f,
            true,
            true,
            true,
            true,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = contentColor
                strokeWidth = borderSize
                isAntiAlias = true
            })

        if (percentCharging <= 100) {
            chargeBitmap?.let { bitmap ->
                val scale = minOf(
                    percentageFillRect!!.width() / bitmap.width,
                    percentageFillRect!!.height() / bitmap.height
                )
                val scaledWidth = (bitmap.width * scale).toInt()
                val scaledHeight = (bitmap.height * scale).toInt()

                val left =
                    percentageFillRect!!.left + (percentageFillRect!!.width() - scaledWidth) / 2
                val top =
                    percentageFillRect!!.top + (percentageFillRect!!.height() - scaledHeight) / 2
                drawBitmap(
                    bitmap, left, top, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        isAntiAlias = true
                        colorFilter = PorterDuffColorFilter(borderColor, PorterDuff.Mode.SRC_IN)
                    })
            }
        }

    }


}