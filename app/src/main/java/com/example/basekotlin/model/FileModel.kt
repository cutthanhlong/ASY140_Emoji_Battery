package com.example.basekotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageModel(
    var name: String, var code: String, var image: Int, var active: Boolean,
) : Parcelable

@Parcelize
data class IntroModel(
    var title: Int = -1, var content: Int = -1, var image: Int = 0,
) : Parcelable

@Parcelize
data class NotchModel(
    var id: Int, var image: Int, var isActive: Boolean,
) : Parcelable

@Parcelize
data class GestureModel(
    var id: Int, var title: String, var isActive: Boolean,
) : Parcelable

@Parcelize
data class StatusBarCustomModel(
    var id: Int, var image: Int,var title: String,
) : Parcelable

@Parcelize
data class DataStyleModel(
    var id: Int, var title: String, var isActive: Boolean,
) : Parcelable