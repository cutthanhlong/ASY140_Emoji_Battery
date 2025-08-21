package com.example.basekotlin.data.call_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmojiDataVMFactory(private val repository: EmojiDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmojiDataVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmojiDataVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}