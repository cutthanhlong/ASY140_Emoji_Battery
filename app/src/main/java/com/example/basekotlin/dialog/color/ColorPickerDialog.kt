package com.example.basekotlin.dialog.color

import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.base.change
import com.example.basekotlin.base.mutableLiveData
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.DialogColorPickerBinding
import com.example.basekotlin.util.customview.battery.fromColor
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorPickerDialog(context: Context, var onClick: (String) -> Unit) :
    BaseDialog<DialogColorPickerBinding>(context, false) {

    override fun setBinding(): DialogColorPickerBinding {
        return DialogColorPickerBinding.inflate(layoutInflater)
    }

    private var currentColor = mutableLiveData(fromColor("#000000"))
    private var color = "#000000"

    override fun initView() {
        val colorFilter = ColorEnvelopeListener { envelope, fromUser ->
            color = "#${envelope.hexCode}"
            Log.e("check_color", "color: $color")
        }
        binding.colorPickerView.setColorListener(colorFilter)
        binding.colorPickerView.setInitialColor(currentColor.value!!)
        binding.colorPickerView.attachAlphaSlider(binding.alphaSlideBar)
        binding.colorPickerView.attachBrightnessSlider(binding.brightnessSlide)
        binding.brightnessSlide.colorPickerView?.setColorListener(colorFilter)

        window!!.setGravity(Gravity.BOTTOM)
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun bindView() {
        binding.apply {
            tvCancel.tap { dismiss() }

            tvSave.tap {
                dismiss()
                val output = if (color.length >= 9) color.replace("#FF", "#") else color
                onClick.invoke(output)
            }

        }
        currentColor.change {
            binding.colorPickerView.actionMode = ActionMode.ALWAYS
            binding.colorPickerView.setInitialColor(it)
            binding.brightnessSlide.colorPickerView?.setInitialColor(it)
            binding.brightnessSlide.post {
                val x = binding.brightnessSlide.width
                val y = binding.brightnessSlide.height / 2
                simulateTouchEvent(binding.brightnessSlide, x.toFloat(), y.toFloat())
            }
        }
    }

    fun showDialog(text: String, color: Int) {
        if (!isShowing) show()

        binding.tvTitle.text = text
        if (color == fromColor("000000")) {
            currentColor.value = fromColor("ffffff")
        } else {
            currentColor.value = color
        }
    }

    private fun simulateTouchEvent(view: View, x: Float, y: Float) {
        val downTime = SystemClock.uptimeMillis()
        val eventTime = SystemClock.uptimeMillis() + 100
        val motionEventDown = MotionEvent.obtain(
            downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0
        )
        view.dispatchTouchEvent(motionEventDown)
        val motionEventUp = MotionEvent.obtain(
            downTime, eventTime + 100, MotionEvent.ACTION_UP, x, y, 0
        )

        view.dispatchTouchEvent(motionEventUp)
        motionEventDown.recycle()
        motionEventUp.recycle()
    }

}