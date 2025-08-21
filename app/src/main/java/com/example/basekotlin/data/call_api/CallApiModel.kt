package com.example.basekotlin.data.call_api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IconModel(
    val code: String,
    val fileName: String,
    val url: String,
    var battery: String = "",
    var isActive: Boolean = false
) : Parcelable

@Parcelize
data class BatteryModel(
    val code: String,
    val fileName: String,
    val url: String,
    var isActive: Boolean = false
) : Parcelable

@Parcelize
data class ColorTemplateModel(
    val code: String,
    val colorBgName: String,
    val hexColor: String,
    val fileName: String,
    val url: String,
    var isActive: Boolean = false
) : Parcelable

@Parcelize
data class BatteryTemplateModel(
    val code: String,
    val batteryName: String,
    val iconName: String,
    var urlBgName: String,
    var urlIconName: String,
    var isActive: Boolean = false
) : Parcelable

@Parcelize
data class EmojiModel(
    val fileName: String,
    val url: String,
    var isActive: Boolean = false
) : Parcelable

@Parcelize
data class AnimationModel(
    val stt: Int,
    val animation: String,
    val animationJsonUrl: String,
    val animationImageUrl: String,
    var isActive: Boolean = false
) : Parcelable