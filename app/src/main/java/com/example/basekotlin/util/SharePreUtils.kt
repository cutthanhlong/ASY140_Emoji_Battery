package com.example.basekotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.basekotlin.data.listener.OnPreferenceChangeListener
import com.google.gson.Gson

class SharedPreUtils {

    companion object {
        var mSharePref: SharedPreferences? = null
        val listeners = mutableListOf<OnPreferenceChangeListener>()
        fun init(context: Context) {
            if (mSharePref == null) {
                mSharePref = PreferenceManager.getDefaultSharedPreferences(context)
            }
        }

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreUtils? = null
        fun getInstance(): SharedPreUtils {
            if (instance == null) {
                instance = SharedPreUtils()
            }
            return instance as SharedPreUtils
        }
    }

    fun registerListener(listener: OnPreferenceChangeListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: OnPreferenceChangeListener) {
        listeners.remove(listener)
    }

    private var lastKey = ""
    fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(mSharePref!!.edit()) {
            block()
            apply()
            notifyListeners(lastKey)
        }
    }

    private fun notifyListeners(key: String) {
        for (listener in listeners) {
            if (key != "") {
                listener.onPreferenceChanged(key)
            }
        }
    }

    fun clearAll() {
        mSharePref?.let {
            it.edit { clear() }
        }
    }

    fun removeValueWithKey(key: String) {
        mSharePref?.let {
            it.edit(commit = true) { remove(key) }
        }
    }

    fun setString(key: String, value: String) {
        edit {
            lastKey = key
            putString(key, value)
        }
    }

    fun getString(key: String, value: String): String {
        lastKey = key
        mSharePref?.let {
            return it.getString(key, value).toString()
        }
        return ""
    }

    fun setInt(key: String, value: Int) {
        edit {
            lastKey = key
            putInt(key, value)
        }
    }

    fun getInt(key: String, value: Int): Int {
        lastKey = key
        mSharePref?.let {
            return it.getInt(key, value)
        }
        return -1
    }

    fun setLong(key: String, value: Long) {
        edit {
            lastKey = key
            putLong(key, value)
        }
    }

    fun getLong(key: String, value: Long): Long {
        lastKey = key
        mSharePref?.let {
            return it.getLong(key, value)
        }
        return -1
    }

    fun setBoolean(key: String, value: Boolean) {
        edit {
            lastKey = key
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String, value: Boolean): Boolean {
        lastKey = key
        return mSharePref?.getBoolean(key, value) ?: true
    }

    fun isRated(context: Context): Boolean {
        val pre = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return pre.getBoolean("rated", false)
    }

    fun forceRated(context: Context) {
        val pre = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        pre.edit {
            putBoolean("rated", true)
        }
    }

    fun isFirstApp(context: Context): Boolean {
        val pre = context.getSharedPreferences("IS_FIRST_LANGUAGE", Context.MODE_PRIVATE)
        return pre.getBoolean("IS_FIRST_APP", false)
    }

    fun setFirstApp(context: Context) {
        val pre = context.getSharedPreferences("IS_FIRST_LANGUAGE", Context.MODE_PRIVATE)
        pre.edit {
            putBoolean("IS_FIRST_APP", true)
        }
    }

    fun getCountOpenApp(context: Context): Int {
        val pre = context.getSharedPreferences("IS_COUNT_OPEN_APP", Context.MODE_PRIVATE)
        return pre.getInt("IS_COUNT_OPEN_APP", 0)
    }

    fun setCountOpenApp(context: Context) {
        val pre = context.getSharedPreferences("IS_COUNT_OPEN_APP", Context.MODE_PRIVATE)
        pre.edit {
            putInt("IS_COUNT_OPEN_APP", pre.getInt("IS_COUNT_OPEN_APP", 0) + 1)
        }
    }

    fun setObject(context: Context, key: String?, value: Any?) {
        if (mSharePref == null) init(context)
        val gson = Gson()
        val json = gson.toJson(value)
        mSharePref!!.edit {
            putString(key, json)
        }
    }

    fun getObject(context: Context, key: String?, classObj: Class<*>?): Any {
        if (mSharePref == null) init(context)
        val gson = Gson()
        val json = mSharePref!!.getString(key, "")
        return gson.fromJson(json, classObj)
    }

}