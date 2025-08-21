package com.example.basekotlin.dialog.permission

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.DialogPermissionBinding
import com.example.basekotlin.util.PermissionManager

class PermissionDialog(context: Context, var onClick: () -> Unit) :
    BaseDialog<DialogPermissionBinding>(context, false) {

    override fun setBinding(): DialogPermissionBinding {
        return DialogPermissionBinding.inflate(layoutInflater)
    }

    private var isAccept = false

    override fun initView() {
        isAccept = PermissionManager.checkFullPermission(context)
        setCheckAccept()
    }

    override fun bindView() {
        binding.ivClose.tap { dismiss() }

        binding.tvDecline.tap { dismiss() }

        binding.tvAccept.tap {
            if (PermissionManager.checkFullPermission(context)) {
                dismiss()
            } else {
                if (isAccept) {
                    dismiss()
                    onClick.invoke()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.You_have_not_accepted_the_terms_of_service),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.ivRadio.tap {
            if (!PermissionManager.checkFullPermission(context)) {
                isAccept = !isAccept
                setCheckAccept()
            }
        }
    }

    private fun setCheckAccept() {
        binding.apply {
            ivRadio.setImageResource(if (isAccept) R.drawable.ic_radio_s else R.drawable.ic_radio_sn)

            tvAccept.setTextColor(if (isAccept) "#FFFFFF".toColorInt() else "#5F5F5F".toColorInt())
            tvAccept.setBackgroundResource(if (isAccept) R.drawable.bg_radius_100_gradient else R.drawable.bg_radius_100)

            if (PermissionManager.checkFullPermission(context)){
                tvDecline.gone()
            }
        }
    }

}