package com.example.basekotlin.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import java.io.IOException
import kotlin.math.ceil

object Utils {
    const val STORAGE = "STORAGE"

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getListPathFromAssets(context: Context, dirFrom: String): ArrayList<String> {
        val listBackground: ArrayList<String> = ArrayList<String>()
        val res: Resources = context.resources
        val am = res.assets
        var fileList: Array<String?>? = arrayOfNulls(0)
        try {
            fileList = am.list(dirFrom)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (fileList != null) {
            for (s in fileList) {
                listBackground.add("file:///android_asset/$dirFrom/$s")
            }
        }
        return listBackground
    }

    fun Int.dpToPx(): Float {
        val density = Resources.getSystem().displayMetrics.density
        return this * density
    }

    fun getHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getRealMetrics(
            displayMetrics
        )
        return displayMetrics.heightPixels
    }

    fun getWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getRealMetrics(
            displayMetrics
        )
        return displayMetrics.widthPixels
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (identifier > 0) {
            resources.getDimensionPixelSize(identifier)
        } else {
            ceil(24 * resources.displayMetrics.density).toInt()
        }
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi / 160f)
    }

    val isApi33toHigher: Boolean
        get() {
            return Build.VERSION.SDK_INT >= 33
        }

    val isApi23toHigher: Boolean
        get() {
            return true
        }

    fun setImageFromAsset(context: Context, imageView: ImageView, assetPath: String) {
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open(assetPath)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}