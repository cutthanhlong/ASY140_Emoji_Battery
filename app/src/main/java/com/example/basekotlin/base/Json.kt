package com.example.basekotlin.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.Serializable

fun <A> String.fromJson(type: Class<A>): A? {
    return try {
        Gson().fromJson(this, type)
    } catch (e: JsonSyntaxException) {
        Log.e("JSON_PARSE_ERROR", "Failed to parse JSON: $this", e)
        null
    }
}

fun <A> A.toJson(): String {
    return Gson().toJson(this)
}


@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Bundle.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getSerializable(
        key, T::class.java
    )
    else getSerializable(key) as? T
}


fun String.format(): String {
    return this.replace("\n", "").trim()
}

fun String.checkValue(): Boolean {
    return this.replace(" ", "").replace("\n", "").isNotEmpty()
}

fun String.maxLength(length: Int): Boolean {
    return this.replace("\n", "").length <= (length)
}

fun String.minLength(length: Int): Boolean {
    return this.replace("\n", "").length >= (length)
}

fun String.formatWithCommas(chunked: Int = 3): String {
    return this.reversed().chunked(chunked).joinToString(",").reversed()
}

fun Number.formatWithCommas(chunked: Int = 3): String {
    return this.toString().reversed().chunked(chunked).joinToString(",").reversed()
}

fun String.isEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
