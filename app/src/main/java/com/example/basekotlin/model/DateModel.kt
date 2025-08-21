package com.example.basekotlin.model

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import com.example.basekotlin.R
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import androidx.core.graphics.toColorInt

enum class DateFormat {
    BOLD, ITALIC, NORMAL, BOLD_ITALIC,
}

@Parcelize
data class ItemDate(
    var value: String = "",
    var color: String = "#000000",
    var format: DateFormat = DateFormat.NORMAL
) : Parcelable

@Parcelize
data class DateModel(var index: Int = 0, var eee: ItemDate, var mmm: ItemDate, var dd: ItemDate) :
    Parcelable

fun getModelDate(): DateModel {
    val pattern = "EEE, dd MMM"
    val date: String = if (Build.VERSION.SDK_INT >= 26) {
        val localDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
        localDate.format(formatter)
    } else {
        val currentDate = Date()
        val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
        sdf.format(currentDate)
    }

    val parts = date.split(", ")
    val day = parts[0]  // EEE (e.g., Sun)
    val monthAndDay = parts[1].split(" ")
    val dayNumber = monthAndDay[0]  // dd (e.g., 19)
    val month = monthAndDay[1]      // MMM (e.g., Jan)

    return DateModel(
        eee = ItemDate(day),
        mmm = ItemDate(month),
        dd = ItemDate(dayNumber)
    )
}


val getEee: String
    get() {
        val pattern = "EEE, dd MMM"
        val date: String = if (Build.VERSION.SDK_INT >= 26) {
            val localDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
            localDate.format(formatter)
        } else {
            val currentDate = Date()
            val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
            sdf.format(currentDate)
        }

        val parts = date.split(", ")
        return parts[0]  
    }
val getMmm: String
    get() {
        val pattern = "EEE, dd MMM"
        val date: String = if (Build.VERSION.SDK_INT >= 26) {
            val localDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
            localDate.format(formatter)
        } else {
            val currentDate = Date()
            val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
            sdf.format(currentDate)
        }

        val parts = date.split(", ")
        return parts[1].split(" ")[1]  // MMM (e.g., Jan)
    }
val getDd: String
    get() {
        val pattern = "EEE, dd MMM"
        val date: String = if (Build.VERSION.SDK_INT >= 26) {
            val localDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
            localDate.format(formatter)
        } else {
            val currentDate = Date()
            val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
            sdf.format(currentDate)
        }

        val parts = date.split(", ")
        return parts[1].split(" ")[0]
    }


fun styleDateModel(context: Context, dateModel: DateModel): SpannableString {
    val fullString = "${dateModel.eee.value} ${dateModel.mmm.value} ${dateModel.dd.value}"
    val spannable = SpannableString(fullString)

    applyStyle(context, spannable, dateModel.eee, 0, dateModel.eee.value.length)
    applyStyle(context,
        spannable,
        dateModel.mmm,
        dateModel.eee.value.length + 2,
        dateModel.eee.value.length + 2 + dateModel.mmm.value.length
    )
    applyStyle(context,
        spannable,
        dateModel.dd,
        fullString.length - dateModel.dd.value.length - 1,  // Adjusted to account for the added space
        fullString.length - 1
    )

    return spannable
}
class CustomTypefaceSpan(family: String, private val newType: Typeface) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}

private fun applyStyle(context: Context,spannable: SpannableString, itemDate: ItemDate, start: Int, end: Int) {
    spannable.setSpan(
        ForegroundColorSpan(itemDate.color.toColorInt()),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    when (itemDate.format) {
        DateFormat.BOLD -> {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val customTypeface = ResourcesCompat.getFont(context, R.font.inter_700)
            customTypeface?.let {
                spannable.setSpan(CustomTypefaceSpan("", it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        DateFormat.ITALIC -> {
            spannable.setSpan(
                StyleSpan(Typeface.ITALIC),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val customTypeface = ResourcesCompat.getFont(context, R.font.inter_500)
            customTypeface?.let {
                spannable.setSpan(CustomTypefaceSpan("", it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        DateFormat.BOLD_ITALIC -> {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD_ITALIC),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val customTypeface = ResourcesCompat.getFont(context, R.font.inter_700)
            customTypeface?.let {
                spannable.setSpan(CustomTypefaceSpan("", it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        DateFormat.NORMAL -> {
            val customTypeface = ResourcesCompat.getFont(context, R.font.inter_500)
            customTypeface?.let {
                spannable.setSpan(CustomTypefaceSpan("", it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}



fun getFormattedDate(): String {
    if (Build.VERSION.SDK_INT >= 26) {
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd", Locale.getDefault())
        return date.format(formatter)
    } else {
        val date = Date()
        val sdf = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
        return sdf.format(date)
    }
}