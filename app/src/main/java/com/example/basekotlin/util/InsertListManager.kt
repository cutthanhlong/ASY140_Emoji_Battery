package com.example.basekotlin.util

import android.widget.ImageView
import com.example.basekotlin.MyApplication
import com.example.basekotlin.R
import com.example.basekotlin.model.DataStyleModel
import com.example.basekotlin.model.GestureModel
import com.example.basekotlin.model.IntroModel
import com.example.basekotlin.model.LanguageModel
import com.example.basekotlin.model.NotchModel
import com.example.basekotlin.model.StatusBarCustomModel


object InsertListManager {

    fun getListLanguage(): MutableList<LanguageModel> {
        val listLanguage: MutableList<LanguageModel> = ArrayList()
        listLanguage.add(LanguageModel("Hindi", "hi", R.drawable.ic_lang_hi, false))
        listLanguage.add(LanguageModel("Spanish", "es", R.drawable.ic_lang_es, false))
        listLanguage.add(LanguageModel("French", "fr", R.drawable.ic_lang_fr, false))
        listLanguage.add(LanguageModel("English", "en", R.drawable.ic_lang_en, false))
        listLanguage.add(LanguageModel("German", "de", R.drawable.ic_lang_de, false))
        listLanguage.add(LanguageModel("Indonesian", "in", R.drawable.ic_lang_in, false))
        listLanguage.add(LanguageModel("Portuguese", "pt", R.drawable.ic_lang_pt, false))
        listLanguage.add(LanguageModel("Chinese", "zh", R.drawable.ic_lang_zh, false))
        return listLanguage
    }

    fun getListDots(dots: Array<ImageView>): ArrayList<ImageView> {
        val listDots: ArrayList<ImageView> = arrayListOf()
        for (it in dots) {
            listDots.add(it)
        }
        return listDots
    }

    fun getListIntro(): ArrayList<IntroModel> {
        val listIntro: ArrayList<IntroModel> = arrayListOf()
        listIntro.add(
            IntroModel(
                R.string.title_intro_01, R.string.content_intro_01, R.drawable.iv_intro_01
            )
        )
        listIntro.add(
            IntroModel(
                R.string.title_intro_02, R.string.content_intro_02, R.drawable.iv_intro_02
            )
        )
        listIntro.add(
            IntroModel(
                R.string.title_intro_03, R.string.content_intro_03, R.drawable.iv_intro_03
            )
        )
        listIntro.add(
            IntroModel(
                R.string.title_intro_04, R.string.content_intro_04, R.drawable.iv_intro_04
            )
        )
        return listIntro
    }

    fun getListNotch(): ArrayList<NotchModel> {
        val list = arrayListOf<NotchModel>()
        list.add(NotchModel(0, R.drawable.notch_data_00, false))
        list.add(NotchModel(1, R.drawable.notch_data_01, false))
        list.add(NotchModel(2, R.drawable.notch_data_02, false))
        list.add(NotchModel(3, R.drawable.notch_data_03, false))
        list.add(NotchModel(4, R.drawable.notch_data_04, false))
        list.add(NotchModel(5, R.drawable.notch_data_05, false))
        list.add(NotchModel(6, R.drawable.notch_data_06, false))
        list.add(NotchModel(7, R.drawable.notch_data_07, false))
        list.add(NotchModel(8, R.drawable.notch_data_08, false))
        list.add(NotchModel(9, R.drawable.notch_data_09, false))
        list.add(NotchModel(10, R.drawable.notch_data_10, false))
        list.add(NotchModel(11, R.drawable.notch_data_11, false))
        list.add(NotchModel(12, R.drawable.notch_data_12, false))
        list.add(NotchModel(13, R.drawable.notch_data_13, false))
        list.add(NotchModel(14, R.drawable.notch_data_14, false))
        list.add(NotchModel(15, R.drawable.notch_data_15, false))
        return list
    }

    fun getListGestureAction(): ArrayList<GestureModel> {
        val list = arrayListOf<GestureModel>()
        list.add(GestureModel(0, MyApplication.context.getString(R.string.do_nothing), false))
        list.add(GestureModel(1, MyApplication.context.getString(R.string.back_action), false))
        list.add(GestureModel(2, MyApplication.context.getString(R.string.home_action), false))
        list.add(GestureModel(3, MyApplication.context.getString(R.string.take_screenshot), false))
        list.add(GestureModel(4, MyApplication.context.getString(R.string.recent_action), false))
        list.add(GestureModel(5, MyApplication.context.getString(R.string.lock_screen), false))
        list.add(GestureModel(6, MyApplication.context.getString(R.string.power_options), false))
        list.add(GestureModel(7, MyApplication.context.getString(R.string.open_control_center), false))
        list.add(GestureModel(8, MyApplication.context.getString(R.string.open_notification), false))
        list.add(GestureModel(9, MyApplication.context.getString(R.string.quick_scroll_to_top), false))
        return list
    }

    fun getListStatusBarCustom(): ArrayList<StatusBarCustomModel> {
        val list = arrayListOf<StatusBarCustomModel>()
        list.add(StatusBarCustomModel(0, R.drawable.ic_status_bar_custom_battery, MyApplication.context.getString(R.string.Emoji_Battery)))
        list.add(StatusBarCustomModel(1, R.drawable.ic_status_bar_custom_wifi, MyApplication.context.getString(R.string.Wifi)))
        list.add(StatusBarCustomModel(2, R.drawable.ic_status_bar_custom_data, MyApplication.context.getString(R.string.Data)))
        list.add(StatusBarCustomModel(3, R.drawable.ic_status_bar_custom_signal, MyApplication.context.getString(R.string.Signal)))
        list.add(StatusBarCustomModel(4, R.drawable.ic_status_bar_custom_airplane, MyApplication.context.getString(R.string.Airplane)))
        list.add(StatusBarCustomModel(5, R.drawable.ic_status_bar_custom_hotspot, MyApplication.context.getString(R.string.Hotspot)))
        list.add(StatusBarCustomModel(6, R.drawable.ic_status_bar_custom_ringer, MyApplication.context.getString(R.string.Ringer)))
        list.add(StatusBarCustomModel(7, R.drawable.ic_status_bar_custom_date_time, MyApplication.context.getString(R.string.Date_Time)))
        list.add(StatusBarCustomModel(8, R.drawable.ic_status_bar_custom_notch, MyApplication.context.getString(R.string.notch)))
        list.add(StatusBarCustomModel(9, R.drawable.ic_status_bar_custom_name, MyApplication.context.getString(R.string.Carrier_Name)))
        list.add(StatusBarCustomModel(10, R.drawable.ic_status_bar_custom_animation, MyApplication.context.getString(R.string.Animation)))
        list.add(StatusBarCustomModel(11, R.drawable.ic_status_bar_custom_charge, MyApplication.context.getString(R.string.Charge)))
        list.add(StatusBarCustomModel(12, R.drawable.ic_status_bar_custom_emotion, MyApplication.context.getString(R.string.Emotion)))
        return list
    }

    fun getListDataStyle(): ArrayList<DataStyleModel>{
        val list = arrayListOf<DataStyleModel>()
        list.add(DataStyleModel(0, "2G", false))
        list.add(DataStyleModel(1, "3G", false))
        list.add(DataStyleModel(2, "4G", false))
        list.add(DataStyleModel(3, "5G", false))
        list.add(DataStyleModel(4, "6G", false))
        list.add(DataStyleModel(5, "LTE", false))
        return list
    }

    fun getListCharge(): ArrayList<NotchModel>{
        val list = arrayListOf<NotchModel>()
        list.add(NotchModel(0, R.drawable.ic_charge_1, false))
        list.add(NotchModel(1, R.drawable.ic_charge_2, false))
        list.add(NotchModel(2, R.drawable.ic_charge_3, false))
        list.add(NotchModel(3, R.drawable.ic_charge_4, false))
        list.add(NotchModel(4, R.drawable.ic_charge_5, false))
        list.add(NotchModel(5, R.drawable.ic_charge_6, false))
        return list
    }

}