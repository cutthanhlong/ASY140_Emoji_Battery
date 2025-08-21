package com.example.basekotlin.util

import android.util.Log
import com.example.basekotlin.R


object PrefManager {
    const val KEY_STATUS_BAR_HEIGHT = "statusBarHeight"
    const val KEY_LEFT_MARGIN = "leftMargin"
    const val KEY_RIGHT_MARGIN = "rightMargin"
    const val KEY_BACKGROUND_COLOR = "backGroundViewColor"
    const val KEY_IS_SHOW_STATUS = "showStatus"
    const val KEY_TIME_COLOR = "timeColor"
    const val KEY_TIME_TEXT_SIZE = "timeTextSize"
    const val KEY_PIN_GS_COLOR = "pinGsColor"
    const val KEY_PIN_GE_COLOR = "pinGeColor"


    var statusBarHeight
        get() = SharedPreUtils.getInstance().getInt(KEY_STATUS_BAR_HEIGHT, 33)
        set(value) {
            SharedPreUtils.getInstance().setInt(KEY_STATUS_BAR_HEIGHT, value)
            Log.d("PrefManager", "Status bar  updated to: $value")
        }

    var isShowStatusBar
        get() = SharedPreUtils.getInstance().getBoolean(KEY_IS_SHOW_STATUS, true)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_IS_SHOW_STATUS, value)

    var backGroundViewColor
        get() = SharedPreUtils.getInstance().getString(KEY_BACKGROUND_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BACKGROUND_COLOR, value)

    const val KEY_ICON_COLOR = "icon_color"
    var iconColor
        get() = SharedPreUtils.getInstance().getString(KEY_ICON_COLOR, "#F0F0F0")
        set(value) = SharedPreUtils.getInstance().setString(KEY_ICON_COLOR, value)

    var leftMargin
        get() = SharedPreUtils.getInstance().getInt(KEY_LEFT_MARGIN, 16)
        set(value) {
            SharedPreUtils.getInstance().setInt(KEY_LEFT_MARGIN, value)
            Log.d("PrefManager", "leftMargin updated to: $value")
        }

    var rightMargin
        get() = SharedPreUtils.getInstance().getInt(KEY_RIGHT_MARGIN, 16)
        set(value) {
            SharedPreUtils.getInstance().setInt(KEY_RIGHT_MARGIN, value)
            Log.d("PrefManager", "rightMargin updated to: $value")
        }


    var pinGsColor
        get() = SharedPreUtils.getInstance().getString(KEY_PIN_GS_COLOR, "#FF17FB")
        set(value) = SharedPreUtils.getInstance().setString(KEY_PIN_GS_COLOR, value)

    var pinGeColor
        get() = SharedPreUtils.getInstance().getString(KEY_PIN_GE_COLOR, "#FF8817")
        set(value) = SharedPreUtils.getInstance().setString(KEY_PIN_GE_COLOR, value)

    const val KEY_PIN_DATA_ID = "pin_data_id"
    var pinDataId
        get() = SharedPreUtils.getInstance().getInt(KEY_PIN_DATA_ID, -1)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_PIN_DATA_ID, value)


    const val KEY_BATTERY_CHARGE_ICON = "batteryIcon"
    var batteryChargeIcon
        get() = SharedPreUtils.getInstance().getInt(KEY_BATTERY_CHARGE_ICON, 1)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_BATTERY_CHARGE_ICON, value)

    const val KEY_SHOW_EMOTION = "showEmotion"
    var isShowEmotion
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_EMOTION, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_EMOTION, value)
    const val KEY_EMOTION_PATH = "emotionPath"
    var emotionPath
        get() = SharedPreUtils.getInstance().getString(KEY_EMOTION_PATH, "")
        set(value) = SharedPreUtils.getInstance().setString(KEY_EMOTION_PATH, value)


    const val KEY_SHOW_NOTCH = "showNotchValue"
    var isShowNotch
        get() = SharedPreUtils.getInstance().getInt(KEY_SHOW_NOTCH, 7)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_SHOW_NOTCH, value)


    const val KEY_USE_TEMPLATE = "useTemplate"
    var isUseTemplate
        get() = SharedPreUtils.getInstance().getBoolean(KEY_USE_TEMPLATE, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_USE_TEMPLATE, value)
    const val KEY_TEMPLATE_VALUE = "templateValue"
    var templateValue
        get() = SharedPreUtils.getInstance().getString(KEY_TEMPLATE_VALUE, "")
        set(value) = SharedPreUtils.getInstance().setString(KEY_TEMPLATE_VALUE, value)


    const val KEY_SHOW_BATTERY_PERCENTAGE = "showBatteryPercentage"
    var isShowBatteryPercentage
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_BATTERY_PERCENTAGE, true)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_BATTERY_PERCENTAGE, value)
    const val KEY_BATTERY_PERCENTAGE_SIZE = "battery_percentage_size"
    var batteryPercentageTextSize
        get() = SharedPreUtils.getInstance().getInt(KEY_BATTERY_PERCENTAGE_SIZE, 12)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_BATTERY_PERCENTAGE_SIZE, value)
    const val KEY_BATTERY_PERCENTAGE_COLOR = "batteryPercentageColor"
    var batteryPercentageColor
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_PERCENTAGE_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_PERCENTAGE_COLOR, value)

    //color pin default
    const val KEY_BATTERY_DEFAULT_PERCENTAGE_COLOR = "batteryDefaultPercentageColor"
    var batteryDefaultPercentageColor
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_DEFAULT_PERCENTAGE_COLOR, "#ffffff")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_DEFAULT_PERCENTAGE_COLOR, value)

    const val KEY_BATTERY_DEFAULT_BACKGROUND_COLOR = "batteryDefaultBackgroundColor"
    var batteryDefaultBackgroundColor
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_DEFAULT_BACKGROUND_COLOR, "#000000")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_DEFAULT_BACKGROUND_COLOR, value)

    const val KEY_BATTERY_SIZE = "batterySize"
    var batterySize
        get() = SharedPreUtils.getInstance().getInt(KEY_BATTERY_SIZE, 80)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_BATTERY_SIZE, value)

    const val KEY_EMOJI_SIZE = "emoji_size"
    var emojiSize
        get() = SharedPreUtils.getInstance().getInt(KEY_EMOJI_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_EMOJI_SIZE, value)

    const val KEY_SHOW_EMOJI = "showEmoji"
    var isShowEmoji
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_EMOJI, true)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_EMOJI, value)
    const val KEY_EMOJI_SELECTED_INDEX = "emojiSelectedIndex"
    var emojiSelectedIndex
        get() = SharedPreUtils.getInstance().getString(KEY_EMOJI_SELECTED_INDEX, "https://haiyan116.net/emojidata/icons/icon_01.png")
        set(value) = SharedPreUtils.getInstance().setString(KEY_EMOJI_SELECTED_INDEX, value)

    const val KEY_BATTERY_SELECTED_INDEX = "batterySelectedIndex"
    var batterySelectedIndex
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_SELECTED_INDEX, "https://haiyan116.net/emojidata/batteries/battery_01.png")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_SELECTED_INDEX, value)

    //emoji battery create
    const val KEY_BATTERY_EMOJI_CREATE_ICON = "KEY_BATTERY_EMOJI_CREATE_ICON"
    var pathBatteryEmojiCreateIcon
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_EMOJI_CREATE_ICON, "https://haiyan116.net/emojidata/icons/icon_01.png")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_EMOJI_CREATE_ICON, value)

    const val KEY_BATTERY_EMOJI_CREATE_BATTERY = "KEY_BATTERY_EMOJI_CREATE_BATTERY"
    var pathBatteryEmojiCreateBattery
        get() = SharedPreUtils.getInstance().getString(KEY_BATTERY_EMOJI_CREATE_BATTERY, "https://haiyan116.net/emojidata/batteries/battery_01.png")
        set(value) = SharedPreUtils.getInstance().setString(KEY_BATTERY_EMOJI_CREATE_BATTERY, value)

    // Gesture
    const val KEY_ENABLE_GESTURE = "enableGesture"
    var isEnableGesture
        get() = SharedPreUtils.getInstance().getBoolean(KEY_ENABLE_GESTURE, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_ENABLE_GESTURE, value)
    const val KEY_ENABLE_VIBRATE = "enableVibrate"
    var isEnableVibrate
        get() = SharedPreUtils.getInstance().getBoolean(KEY_ENABLE_VIBRATE, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_ENABLE_VIBRATE, value)
    const val KEY_VIBRATE_DURATION = "vibrateDuration"
    var vibrateDuration
        get() = SharedPreUtils.getInstance().getInt(KEY_VIBRATE_DURATION, 300)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_VIBRATE_DURATION, value)

    const val KEY_GESTURE_SINGLE_TAP = "singleTap"
    var isGestureSingleTap
        get() = SharedPreUtils.getInstance().getInt(KEY_GESTURE_SINGLE_TAP, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_GESTURE_SINGLE_TAP, value)
    const val KEY_GESTURE_SWIPE_LTR = "swipe_left_right"
    var isGestureSwipeLeftRight
        get() = SharedPreUtils.getInstance().getInt(KEY_GESTURE_SWIPE_LTR, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_GESTURE_SWIPE_LTR, value)
    const val KEY_GESTURE_SWIPE_RTL = "swipe_right_left"
    var isGestureSwipeRightLeft
        get() = SharedPreUtils.getInstance().getInt(KEY_GESTURE_SWIPE_RTL, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_GESTURE_SWIPE_RTL, value)
    const val KEY_GESTURE_LONG_PRESS = "longPress"
    var isGestureLongPress
        get() = SharedPreUtils.getInstance().getInt(KEY_GESTURE_LONG_PRESS, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_GESTURE_LONG_PRESS, value)


    const val KEY_WIFI_SIZE = "wifiSize"
    var wifiSize
        get() = SharedPreUtils.getInstance().getInt(KEY_WIFI_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_WIFI_SIZE, value)
    const val KEY_WIFI_COLOR = "wifiColor"
    var wifiColor
        get() = SharedPreUtils.getInstance().getString(KEY_WIFI_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_WIFI_COLOR, value)

    const val KEY_SIGNAL_SIZE = "signalSize"
    var signalSize
        get() = SharedPreUtils.getInstance().getInt(KEY_SIGNAL_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_SIGNAL_SIZE, value)
    const val KEY_SIGNAL_COLOR = "signalColor"
    var signalColor
        get() = SharedPreUtils.getInstance().getString(KEY_SIGNAL_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_SIGNAL_COLOR, value)
    const val KEY_DATA_SIZE = "dataSize"
    var dataSize
        get() = SharedPreUtils.getInstance().getInt(this.KEY_DATA_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(this.KEY_DATA_SIZE, value)
    const val KEY_DATA_COLOR = "dataColor"
    var dataColor
        get() = SharedPreUtils.getInstance().getString(KEY_DATA_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_DATA_COLOR, value)
    const val KEY_DATA_INDEX = "dataIndex"
    var dataInt
        get() = SharedPreUtils.getInstance().getInt(KEY_DATA_INDEX, -1)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_DATA_INDEX, value)


    const val KEY_AIRPLANE_SIZE = "airplaneSize"
    var airplaneSize
        get() = SharedPreUtils.getInstance().getInt(KEY_AIRPLANE_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_AIRPLANE_SIZE, value)
    const val KEY_AIRPLANE_COLOR = "airplaneColor"
    var airplaneColor
        get() = SharedPreUtils.getInstance().getString(KEY_AIRPLANE_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_AIRPLANE_COLOR, value)

    const val KEY_HOTSPOT_SIZE = "hotspotSize"
    var hotspotSize
        get() = SharedPreUtils.getInstance().getInt(KEY_HOTSPOT_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_HOTSPOT_SIZE, value)
    const val KEY_HOTSPOT_COLOR = "hotspotColor"
    var hotspotColor
        get() = SharedPreUtils.getInstance().getString(KEY_HOTSPOT_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_HOTSPOT_COLOR, value)

    const val KEY_RINGER_SIZE = "ringerSize"
    var ringerSize
        get() = SharedPreUtils.getInstance().getInt(KEY_RINGER_SIZE, 20)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_RINGER_SIZE, value)
    const val KEY_RINGER_COLOR = "ringerColor"
    var ringerColor
        get() = SharedPreUtils.getInstance().getString(KEY_RINGER_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_RINGER_COLOR, value)


    const val KEY_SHOW_TIME = "showDate"
    var isShowTime
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_TIME, true)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_TIME, value)
    var timeTextColor
        get() = SharedPreUtils.getInstance().getString(KEY_TIME_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_TIME_COLOR, value)
    var timeTextSize
        get() = SharedPreUtils.getInstance().getInt(KEY_TIME_TEXT_SIZE, 16)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_TIME_TEXT_SIZE, value)
    const val KEY_TIME_SHOW_SECOND = "timeShowSecond"
    var timeShowSecond
        get() = SharedPreUtils.getInstance().getBoolean(KEY_TIME_SHOW_SECOND, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_TIME_SHOW_SECOND, value)
    const val KEY_TIME_APM = "time_show_apm"
    var isTimeShowAmPm
        get() = SharedPreUtils.getInstance().getBoolean(KEY_TIME_APM, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_TIME_APM, value)


    const val KEY_SHOW_DATE = "show_date"
    const val KEY_DATE_SIZE = "date_size"
    const val KEY_DATE_COLOR = "date_color"
    const val KEY_DATE_FORMAT = "date_format"
    var isShowDate
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_DATE, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_DATE, value)
    var dateSize
        get() = SharedPreUtils.getInstance().getInt(KEY_DATE_SIZE, 16)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_DATE_SIZE, value)
    var dateColor
        get() = SharedPreUtils.getInstance().getString(KEY_DATE_COLOR, "#0D0B0B")
        set(value) = SharedPreUtils.getInstance().setString(KEY_DATE_COLOR, value)
    var dateFormatIndex
        get() = SharedPreUtils.getInstance().getInt(KEY_DATE_FORMAT, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_DATE_FORMAT, value)


    const val KEY_SHOW_ANIMATION = "showAnimation"
    var isShowAnimation
        get() = SharedPreUtils.getInstance().getBoolean(KEY_SHOW_ANIMATION, false)
        set(value) = SharedPreUtils.getInstance().setBoolean(KEY_SHOW_ANIMATION, value)
    const val KEY_ANIMATION_SIZE = "animation_size"
    var animationSize
        get() = SharedPreUtils.getInstance().getInt(KEY_ANIMATION_SIZE, 33)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_ANIMATION_SIZE, value)
    const val KEY_ANIMATION_ID = "animation_id"
    var animationId
        get() = SharedPreUtils.getInstance().getInt(KEY_ANIMATION_ID, 0)
        set(value) = SharedPreUtils.getInstance().setInt(KEY_ANIMATION_ID, value)

    const val KEY_ANIMATION_URL = "animation_url"
    var animationURL
        get() = SharedPreUtils.getInstance().getString(KEY_ANIMATION_URL, "https://haiyan116.net/emojidata/animation_json/Animation_01.json")
        set(value) = SharedPreUtils.getInstance().setString(KEY_ANIMATION_URL, value)


    const val KEY_SHOW_CARRIER = "show_carrier"
    var isShowCarrier
        get() = SharedPreUtils.getInstance().getString(KEY_SHOW_CARRIER, "")
        set(value) = SharedPreUtils.getInstance().setString(KEY_SHOW_CARRIER, value)
}

