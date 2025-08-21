package com.example.basekotlin.util.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.basekotlin.R
import kotlin.math.cos
import kotlin.math.sin
import androidx.core.content.withStyledAttributes

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    // Thuộc tính gradient
    private var startColor: Int = Color.BLACK
    private var endColor: Int = Color.WHITE
    private var gradientAngle: Float = 0f // Góc tính bằng độ

    // Paint cho gradient
    private var gradientPaint: Paint? = null

    init {
        // Đọc thuộc tính từ XML nếu có
        attrs?.let {
            context.withStyledAttributes(it, R.styleable.GradientTextView) {
                startColor = getColor(R.styleable.GradientTextView_startColor, Color.BLACK)
                endColor = getColor(R.styleable.GradientTextView_endColor, Color.WHITE)
                gradientAngle = getFloat(R.styleable.GradientTextView_gradientAngle, 0f)
            }
        }

        setupGradient()
    }

    private fun setupGradient() {
        // Tạo gradient paint
        gradientPaint = Paint(paint).apply {
            style = Paint.Style.FILL
        }
        updateGradient()
    }

    private fun updateGradient() {
        if (width > 0 && height > 0) {
            // Chuyển đổi góc từ độ sang radian
            val angleRad = Math.toRadians(gradientAngle.toDouble())

            // Tính toán điểm bắt đầu và kết thúc cho gradient
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = maxOf(width, height) / 2f

            val startX = centerX - (cos(angleRad) * radius).toFloat()
            val startY = centerY - (sin(angleRad) * radius).toFloat()
            val endX = centerX + (cos(angleRad) * radius).toFloat()
            val endY = centerY + (sin(angleRad) * radius).toFloat()

            // Tạo LinearGradient
            val gradient = LinearGradient(
                startX, startY, endX, endY,
                startColor, endColor,
                Shader.TileMode.CLAMP
            )

            gradientPaint?.shader = gradient
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateGradient()
    }

    override fun onDraw(canvas: Canvas) {
        // Vẽ text với gradient
        canvas.let { c ->
            gradientPaint?.let { paint ->
                val text = text.toString()
                if (text.isNotEmpty()) {
                    // Lưu trạng thái canvas
                    c.save()

                    // Tính toán vị trí text
                    val textBounds = Rect()
                    this.paint.getTextBounds(text, 0, text.length, textBounds)

                    val x = paddingLeft.toFloat()
                    val y = (height - paddingTop - paddingBottom) / 2f + textBounds.height() / 2f + paddingTop

                    // Vẽ text với gradient
                    c.drawText(text, x, y, paint)

                    // Khôi phục trạng thái canvas
                    c.restore()
                }
            }
        }
    }

    // Các phương thức public để thay đổi thuộc tính
    fun setStartColor(color: Int) {
        startColor = color
        updateGradient()
        invalidate()
    }

    fun setEndColor(color: Int) {
        endColor = color
        updateGradient()
        invalidate()
    }

    fun setGradientAngle(angle: Float) {
        gradientAngle = angle
        updateGradient()
        invalidate()
    }

    fun setGradientColors(start: Int, end: Int, angle: Float = gradientAngle) {
        startColor = start
        endColor = end
        gradientAngle = angle
        updateGradient()
        invalidate()
    }
}