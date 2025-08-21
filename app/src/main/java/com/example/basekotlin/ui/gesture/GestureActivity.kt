package com.example.basekotlin.ui.gesture

import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityGestureBinding
import com.example.basekotlin.dialog.gesture_action.GestureActionDialog
import com.example.basekotlin.model.EnumGesture
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.PrefManager

class GestureActivity : BaseActivity<ActivityGestureBinding>(ActivityGestureBinding::inflate) {

    private lateinit var gestureActionDialog: GestureActionDialog
    private var type = EnumGesture.SINGLE

    override fun initView() {
        super.initView()

        binding.apply {
            ivGesture.setImageResource(if (PrefManager.isEnableGesture) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            ivVibrate.setImageResource(if (PrefManager.isEnableVibrate) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            viewVibrate.showState(PrefManager.isEnableVibrate)

            sbVibrate.progress = PrefManager.vibrateDuration
            tvVibrate.text = getString(R.string.intensity_s, "${PrefManager.vibrateDuration}")

            tvSingle.text =
                InsertListManager.getListGestureAction()[PrefManager.isGestureSingleTap].title
            tvSwipeLeft.text =
                InsertListManager.getListGestureAction()[PrefManager.isGestureSwipeLeftRight].title
            tvSwipeRight.text =
                InsertListManager.getListGestureAction()[PrefManager.isGestureSwipeRightLeft].title
            tvLong.text =
                InsertListManager.getListGestureAction()[PrefManager.isGestureLongPress].title
        }

        gestureActionDialog = GestureActionDialog(this) {
            when (type) {
                EnumGesture.SINGLE -> {
                    PrefManager.isGestureSingleTap = it.id
                    binding.tvSingle.text = it.title
                }

                EnumGesture.SWIPE_LEFT -> {
                    PrefManager.isGestureSwipeLeftRight = it.id
                    binding.tvSwipeLeft.text = it.title
                }

                EnumGesture.SWIPE_RIGHT -> {
                    PrefManager.isGestureSwipeRightLeft = it.id
                    binding.tvSwipeRight.text = it.title
                }

                EnumGesture.LONG_PRESS -> {
                    PrefManager.isGestureLongPress = it.id
                    binding.tvLong.text = it.title
                }

                else -> {}
            }
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivGesture.tap {
                PrefManager.isEnableGesture = !PrefManager.isEnableGesture
                ivGesture.setImageResource(if (PrefManager.isEnableGesture) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }

            ivVibrate.tap {
                PrefManager.isEnableVibrate = !PrefManager.isEnableVibrate
                ivVibrate.setImageResource(if (PrefManager.isEnableVibrate) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
                viewVibrate.showState(PrefManager.isEnableVibrate)
            }

            btnSingle.tap {
                type = EnumGesture.SINGLE
                showDialogAction(binding.tvSingle.text.toString())
            }

            btnSwipeLeft.tap {
                type = EnumGesture.SWIPE_LEFT
                showDialogAction(binding.tvSwipeLeft.text.toString())
            }

            btnSwipeRight.tap {
                type = EnumGesture.SWIPE_RIGHT
                showDialogAction(binding.tvSwipeRight.text.toString())
            }

            btnLong.tap {
                type = EnumGesture.LONG_PRESS
                showDialogAction(binding.tvLong.text.toString())
            }

            sbVibrate.onProgressChanged { progress, _ ->
                tvVibrate.text = getString(R.string.intensity_s, "$progress")
                PrefManager.vibrateDuration = progress
            }
        }
    }

    private fun showDialogAction(title: String) {
        val item = InsertListManager.getListGestureAction().find { it.title == title }
            ?: InsertListManager.getListGestureAction()[0]
        gestureActionDialog.showDialog(item)
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}