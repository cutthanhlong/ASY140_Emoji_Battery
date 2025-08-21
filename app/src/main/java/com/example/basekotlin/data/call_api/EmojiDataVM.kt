package com.example.basekotlin.data.call_api

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basekotlin.ads.IsNetWork
import kotlinx.coroutines.launch

class EmojiDataVM(private val repository: EmojiDataRepository) : ViewModel() {

    private val _isNetWork = MutableLiveData<Boolean>()
    val isNetWork: LiveData<Boolean> = _isNetWork
    private val _emojiData = MutableLiveData<EmojiDataResponse>()
    val emojiData: LiveData<EmojiDataResponse> = _emojiData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Individual LiveData for each category
    private val _icons = MutableLiveData<List<IconModel>>()
    val icons: LiveData<List<IconModel>> = _icons

    private val _batteries = MutableLiveData<List<BatteryModel>>()
    val batteries: LiveData<List<BatteryModel>> = _batteries

    private val _colorTemplates = MutableLiveData<List<ColorTemplateModel>>()
    val colorTemplates: LiveData<List<ColorTemplateModel>> = _colorTemplates

    private val _batteryTemplates = MutableLiveData<List<BatteryTemplateModel>>()
    val batteryTemplates: LiveData<List<BatteryTemplateModel>> = _batteryTemplates

    private val _emojis = MutableLiveData<List<EmojiModel>>()
    val emojis: LiveData<List<EmojiModel>> = _emojis

    private val _animations = MutableLiveData<List<AnimationModel>>()
    val animations: LiveData<List<AnimationModel>> = _animations

    fun loadEmojiData() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val url =
                    "https://haiyan116.net/emojidata/getdata.php?key=eMiyPtgg5B2Xyd5VrGjso8Zd3Iy4D"
                val data = repository.getEmojiData(url)

                if (data != null) {
                    _emojiData.value = data

                    // Update individual categories
                    _icons.value = data.icons
                    _batteries.value = data.batteries
                    _colorTemplates.value = data.colorTemplates
                    _batteryTemplates.value = data.batteryTemplates
                    _emojis.value = data.emojis
                    _animations.value = data.animations
                } else {
                    _error.value = "Failed to load data"
                }

                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
                Log.e("VIEWMODEL_ERROR", "Error loading data", e)
            }
        }
    }

    fun checkNetWork(activity: Activity) {
        viewModelScope.launch {
            _isNetWork.value = IsNetWork.haveNetworkConnection(activity)
        }
    }
}
