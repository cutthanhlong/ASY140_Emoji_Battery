package com.example.basekotlin.base

import android.content.Context
import android.os.Build
import android.widget.Toast

fun Context.showToastById(id: Int) {
    Toast.makeText(this, resources.getString(id), Toast.LENGTH_SHORT).show()
}

fun Context.getStringById(id: Int): String {
    return resources.getString(id)
}

fun Context.showToastByString(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getCurrentSdkVersion(): Int {
    return Build.VERSION.SDK_INT
}