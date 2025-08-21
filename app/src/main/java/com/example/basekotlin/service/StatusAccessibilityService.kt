package com.example.basekotlin.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Path
import android.graphics.PixelFormat
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.graphics.toColorInt
import com.airbnb.lottie.LottieCompositionFactory
import com.bumptech.glide.Glide
import com.example.basekotlin.R
import com.example.basekotlin.base.change
import com.example.basekotlin.base.fromJson
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.mutableLiveData
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.ColorTemplateModel
import com.example.basekotlin.data.listener.OnPreferenceChangeListener
import com.example.basekotlin.data.listener.OnSwipeTouchListener
import com.example.basekotlin.databinding.StatusBarViewBinding
import com.example.basekotlin.model.DateFormat
import com.example.basekotlin.model.DateModel
import com.example.basekotlin.model.ItemDate
import com.example.basekotlin.model.getDd
import com.example.basekotlin.model.getEee
import com.example.basekotlin.model.getMmm
import com.example.basekotlin.model.styleDateModel
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.PrefManager.pinGeColor
import com.example.basekotlin.util.PrefManager.pinGsColor
import com.example.basekotlin.util.SharedPreUtils
import com.example.basekotlin.util.Utils
import com.example.basekotlin.util.Utils.dpToPx
import com.example.basekotlin.util.Utils.isApi33toHigher
import java.lang.reflect.Method
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

class StatusAccessibilityService : AccessibilityService(), OnPreferenceChangeListener {

    private var TAG = "StatusAccessibilityService"
    private var appContext: Context? = null
    private var manager: WindowManager? = null
    private var statusBarHeight: Int = 0
    private var totalHeight: Int = 0
    private var statusBarWidth: Int = 0
    private var localLayoutParams: WindowManager.LayoutParams? = null
    private var isCharging: Boolean = false

    private val flagNormal = 8913704
    private var accessibilityEvent: AccessibilityEvent? = null

    companion object {
        var instance: StatusAccessibilityService? = null
        fun listDateTemplate() = mutableListOf(
            DateModel(
                0,
                ItemDate("$getEee,", PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate(getMmm, PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate(getDd, PrefManager.dateColor, DateFormat.NORMAL)
            ), DateModel(
                1,
                ItemDate("$getEee,", "#FD0000", DateFormat.NORMAL),
                ItemDate(getMmm, PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate("\n$getDd", PrefManager.dateColor, DateFormat.BOLD)
            ), DateModel(
                2,
                ItemDate(getEee, "#FD0000", DateFormat.BOLD),
                ItemDate("", PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate("\n$getDd", PrefManager.dateColor, DateFormat.BOLD)
            ), DateModel(
                3,
                ItemDate("", PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate(getMmm, PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate(getDd, "#FD0000", DateFormat.NORMAL)
            ), DateModel(
                4,
                ItemDate(getEee, PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate("", PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate("", PrefManager.dateColor, DateFormat.NORMAL)
            ), DateModel(
                5,
                ItemDate(getEee, "#FD0000", DateFormat.NORMAL),
                ItemDate("", PrefManager.dateColor, DateFormat.NORMAL),
                ItemDate("\n$getDd", PrefManager.dateColor, DateFormat.NORMAL)
            )
        )
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        this@StatusAccessibilityService.accessibilityEvent = event
    }

    private val mBatInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val intExtra = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1)
            val intExtra2 =
                ((intent.getIntExtra("level", -1) * 100) / intent.getIntExtra("scale", -1))
            binding.tvPinDefault.text = "$intExtra2%"
            binding.textPin.text = "$intExtra2%"
            binding.textPinCharging.text = "$intExtra2"
            if (intExtra == 2 || intExtra == 5) {
                this@StatusAccessibilityService.isCharging = true
                if (intExtra2 < 100) {
                    binding.llCharging.visible()
                    binding.chargingValue.setCharging(intExtra2.toFloat())
                } else {
                    binding.llCharging.gone()
                }

            } else {
                binding.llCharging.gone()
                this@StatusAccessibilityService.isCharging = false
            }
        }
    }


    private var isWifiConnected: Boolean = false
    private var isLTEConnected: Boolean = false
    var airPlaneMode = mutableLiveData(false)
    private var mInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            when (action) {
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    // Xử lý Airplane Mode
                    airPlaneMode.value = intent.getBooleanExtra("state", false)
                }

                "android.net.conn.CONNECTIVITY_CHANGE" -> {
                    // Xử lý thay đổi kết nối mạng
                    val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork = connectivityManager.activeNetworkInfo

                    when {
                        // Không có mạng
                        activeNetwork == null || !activeNetwork.isConnected -> {
                            binding.icWifi.gone()
                            binding.icData.gone()
                            binding.icSignal.visible()
                        }

                        // Wi-Fi đang kết nối
                        activeNetwork.type == ConnectivityManager.TYPE_WIFI -> {
                            binding.icWifi.visible()
                            binding.icData.gone()
                            binding.icSignal.gone()
                        }

                        // Mobile Data (LTE/3G/4G/5G)
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> {
                            binding.icWifi.gone()
                            binding.icData.visible()
                            binding.icSignal.gone()
                        }
                    }
                }

                "android.net.wifi.WIFI_AP_STATE_CHANGED" -> {
                    // Xử lý thay đổi WiFi Hotspot
                    isHotspotEnabled.value = isHotspotEnabled()
                }

                AudioManager.RINGER_MODE_CHANGED_ACTION -> {
                    // Xử lý thay đổi chế độ chuông
                    appContext?.let {
                        handleRingerModeChanged(it, object :
                            RingerListener {
                            override fun onModeDefault() {
                                super.onModeDefault()
                                binding.icRinger.gone()
                            }

                            override fun onModeNormal() {
                                super.onModeNormal()
                                binding.icRinger.gone()
                            }

                            override fun onModeSilent() {
                                super.onModeSilent()
                                binding.icRinger.visible()
                                loadRinger(R.drawable.ic_ringer_off)
                            }

                            override fun onModeVibrate() {
                                super.onModeVibrate()
                                binding.icRinger.visible()
                                loadRinger(R.drawable.ic_ringer_vibrate)
                            }
                        })
                    }
                }

                Intent.ACTION_SCREEN_OFF -> {
                    Log.e("TEST", "📴 SCREEN OFF - luôn locked")
                    val keyguardManager =
                        context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager

                    Log.e(
                        "check_carrier_name",
                        "isKeyguardLocked: " + keyguardManager.isKeyguardLocked
                    )
                    Log.e("check_carrier_name", "action: " + intent.action)

                    if (keyguardManager.isKeyguardLocked) {
                        if (PrefManager.isShowCarrier != "") {
                            binding.elementNetworkName.visible()
                            binding.ltAnimation.gone()
                            binding.elementNetworkName.text = PrefManager.isShowCarrier

                            binding.elementTime.gone()
                        } else {
                            binding.elementNetworkName.gone()
                            binding.elementTime.visible()
                        }
                        binding.textDate.gone()
                        return
                    }
                }

                Intent.ACTION_SCREEN_ON -> {
                    Log.e("TEST", "💡 SCREEN ON - màn hình sáng nhưng chưa chắc đã unlock")
                    val keyguardManager =
                        context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager

                    Log.e(
                        "check_carrier_name",
                        "isKeyguardLocked: " + keyguardManager.isKeyguardLocked
                    )
                    Log.e("check_carrier_name", "action: " + intent.action)

                    if (keyguardManager.isKeyguardLocked) {
                        if (PrefManager.isShowCarrier != "") {
                            binding.elementNetworkName.visible()
                            binding.ltAnimation.gone()
                            binding.elementNetworkName.text = PrefManager.isShowCarrier

                            binding.elementTime.gone()
                        } else {
                            binding.elementNetworkName.gone()
                            binding.elementTime.visible()
                        }
                        binding.textDate.gone()
                        return
                    } else {
                        binding.elementNetworkName.gone()
                        binding.elementTime.visible()
                        if (PrefManager.isShowDate) {
                            binding.textDate.visible()
                        }
                        if (PrefManager.isShowAnimation) {
                            binding.ltAnimation.visible()
                        }
                    }
                }

                Intent.ACTION_USER_PRESENT -> {
                    Log.e("TEST", "🔓 USER PRESENT - mở khoá thành công")
                    val keyguardManager =
                        context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager

                    Log.e(
                        "check_carrier_name",
                        "isKeyguardLocked: " + keyguardManager.isKeyguardLocked
                    )
                    Log.e("check_carrier_name", "action: " + intent.action)

                    if (!keyguardManager.isKeyguardLocked) {
                        binding.elementNetworkName.gone()
                        binding.elementTime.visible()
                        if (PrefManager.isShowDate) {
                            binding.textDate.visible()
                        }
                        if (PrefManager.isShowAnimation) {
                            binding.ltAnimation.visible()
                        }
                    }
                }
            }


            /*if (action != null) {

                if (action.matches("android.intent.action.AIRPLANE_MODE".toRegex())) {
                    airPlaneMode.value = intent.getBooleanExtra("state", false)
                }
                if (action!!.matches("android.net.conn.CONNECTIVITY_CHANGE".toRegex())) {
                    val connectivityManager =
                        this@StatusAccessibilityService.getSystemService("connectivity") as ConnectivityManager
                    connectivityManager?.let {
                        try {
                            state = connectivityManager.getNetworkInfo(0)!!.state
                            state2 = connectivityManager.getNetworkInfo(1)!!.state
                            if (state != NetworkInfo.State.CONNECTED && state != NetworkInfo.State.CONNECTING) {
                                if (state2 != NetworkInfo.State.CONNECTED && state2 != NetworkInfo.State.CONNECTING) {
                                    isWifiConnected = false
                                    isLTEConnected = false
                                    binding.icWifi.gone()
                                    binding.icData.gone()
                                    binding.icSignal.visible()
                                } else {
                                    // WiFi is connected
                                    isWifiConnected = true
                                    isLTEConnected = false
                                    binding.icWifi.visible()
                                    binding.icData.gone()
                                    binding.icSignal.gone()
                                }
                            } else {

                                isWifiConnected = false
                                isLTEConnected = true
                                binding.icWifi.gone()
                                binding.icData.visible()
                                binding.icSignal.gone()
                            }
                        } catch (e: NullPointerException) {
                            Log.d("MediaRouteProviderProtocol", e.message!!)
                        } catch (e2: RuntimeException) {
                            Log.d("MediaRouteProviderProtocol", e2.message!!)
                        } catch (e3: java.lang.Exception) {
                            Log.d("MediaRouteProviderProtocol", e3.message!!)
                        }
                    } ?: run {
                        Toast.makeText(
                            appContext,
                            "Can't check internet connection change",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                if ("android.net.wifi.WIFI_AP_STATE_CHANGED" == action) {
                    isHotspotEnabled.value = isHotspotEnabled()
                }
                if (action.matches("android.media.RINGER_MODE_CHANGED".toRegex())) {
                    appContext?.let {
                        handleRingerModeChanged(it, object :
                            RingerListener {
                            override fun onModeDefault() {
                                super.onModeDefault()
                                binding.icRinger.gone()
                            }

                            override fun onModeNormal() {
                                super.onModeNormal()
                                binding.icRinger.gone()
                            }

                            override fun onModeSilent() {
                                super.onModeSilent()
                                binding.icRinger.visible()
                                loadRinger(R.drawable.ic_ringer_off)
                            }

                            override fun onModeVibrate() {
                                super.onModeVibrate()
                                binding.icRinger.visible()
                                loadRinger(R.drawable.ic_ringer_vibrate)
                            }
                        })
                    }
                }

                if (action == "android.intent.action.USER_PRESENT" || action == "android.intent.action.SCREEN_OFF" || action == "android.intent.action.SCREEN_ON") {
                    val keyguardManager =
                        (appContext as StatusAccessibilityService).getSystemService("keyguard") as KeyguardManager
                    if (keyguardManager.inKeyguardRestrictedInputMode()) {
                        if (PrefManager.isShowCarrier != "") {
                            binding.elementNetworkName.visible()
                            binding.ltAnimation.gone()
                            binding.elementNetworkName.text = PrefManager.isShowCarrier

                            binding.elementTime.gone()
                        } else {
                            binding.elementNetworkName.gone()
                            binding.elementTime.visible()
                        }
                        binding.textDate.gone()
                    }else {
                        binding.elementNetworkName.gone()
                        binding.elementTime.visible()
                        if (PrefManager.isShowDate) {
                            binding.textDate.visible()
                        }
                        if (PrefManager.isShowAnimation) {
                            binding.ltAnimation.visible()
                        }
                    }
                }
            }*/
        }
    }

    fun actionPresent() {
        val keyguardManager =
            appContext?.let { it.getSystemService(KEYGUARD_SERVICE) as KeyguardManager }

        keyguardManager?.let {
            if (!it.isKeyguardLocked) {
                binding.elementNetworkName.gone()
                binding.elementTime.visible()
                if (PrefManager.isShowDate) {
                    binding.textDate.visible()
                }
                if (PrefManager.isShowAnimation) {
                    binding.ltAnimation.visible()
                }
            }
        }
    }

    lateinit var binding: StatusBarViewBinding

    override fun onInterrupt() {

    }

    val layoutParams = WindowManager.LayoutParams()
    var receiverRotateScreen: ConfigurationChangeReceiver? = null


    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
        ) == 1
    }

    interface RingerListener {
        fun onModeSilent() {}
        fun onModeVibrate() {}
        fun onModeNormal() {}
        fun onModeDefault() {}
    }

    fun handleRingerModeChanged(context: Context, ringerListener: RingerListener) {
        val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
        val ringerMode = audioManager.ringerMode

        when (ringerMode) {
            AudioManager.RINGER_MODE_SILENT -> {
                ringerListener.onModeSilent()
            }

            AudioManager.RINGER_MODE_VIBRATE -> {
                ringerListener.onModeVibrate()
            }

            AudioManager.RINGER_MODE_NORMAL -> {
                ringerListener.onModeNormal()
            }

            else -> {
                ringerListener.onModeDefault()
            }
        }
    }

    fun isHotspotEnabled(): Boolean? {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val method: Method = wifiManager.javaClass.getMethod(
            "getWifiApState"
        )
        method.isAccessible = true
        val invoke = method.invoke(wifiManager)
        return invoke == 13
    }

    private var isHotspotEnabled = mutableLiveData(false)

    @SuppressLint("WrongConstant", "ClickableViewAccessibility")
    override fun onServiceConnected() {
        instance = this

        manager = getSystemService(WINDOW_SERVICE) as WindowManager
        SharedPreUtils.init(this)
        SharedPreUtils.getInstance().registerListener(this@StatusAccessibilityService)
        appContext = this
        statusBarHeight = Utils.getStatusBarHeight(this)
        statusBarHeight = (statusBarHeight + Utils.convertDpToPixel(
            1.0f, appContext as StatusAccessibilityService
        )).toInt()
        totalHeight = Utils.getHeight(this)
        statusBarWidth = Utils.getWidth(this)
        Log.d(TAG, "SizeStatus: $statusBarHeight/$totalHeight")

        binding = StatusBarViewBinding.inflate(LayoutInflater.from(this))
        airPlaneMode.value = isAirplaneModeOn(this@StatusAccessibilityService)
        isHotspotEnabled.value = isHotspotEnabled()


        this.screenHeight = Utils.getHeight(this@StatusAccessibilityService)
        localLayoutParams = layoutParams.apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            gravity = Gravity.TOP
            flags = if (PrefManager.isEnableGesture) flagNormal else flagNormal or 16
            softInputMode = SOFT_INPUT_ADJUST_RESIZE
            height = statusBarHeight
            width = WindowManager.LayoutParams.MATCH_PARENT
            format = PixelFormat.TRANSLUCENT
            y = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        if (PrefManager.isEnableGesture) {
            binding.llAction.visible()
        } else {
            binding.llAction.gone()
        }
        binding.llAction.setOnTouchListener(object :
            OnSwipeTouchListener(this@StatusAccessibilityService) {
            override fun onSingleTap() {
                super.onSingleTap()
                switchAction(PrefManager.isGestureSingleTap)
                Log.d(TAG, "onSwipeTop: Action Single Tap")
            }

            override fun onMyLongPress() {
                super.onMyLongPress()
                switchAction(PrefManager.isGestureLongPress)
                Log.d(TAG, "onSwipeTop: Action Long Press")
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                switchAction(PrefManager.isGestureSwipeRightLeft)
                Log.d(TAG, "onSwipeTop: Action Swipe Left")
            }

            override fun onSwipeBottom() {
                super.onSwipeBottom()
                Log.d(TAG, "onSwipeTop: Action Top")
                performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS)
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                switchAction(PrefManager.isGestureSwipeLeftRight)
                Log.d(TAG, "onSwipeTop: Action Swipe Right")
            }
        })

        try {
            manager!!.addView(binding.root, localLayoutParams)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Unfortunately something didn't work. Please try again or contact the developer.",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.loadData(this@StatusAccessibilityService)
        if (isApi33toHigher) {
            this.appContext?.registerReceiver(
                this.mBatInfoReceiver, IntentFilter("android.intent.action.BATTERY_CHANGED")
            )
        } else {
            this.appContext?.registerReceiver(
                this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.SCREEN_ON")
        intentFilter.addAction("android.intent.action.SCREEN_OFF")
        intentFilter.addAction("android.intent.action.USER_PRESENT")
        intentFilter.addAction("android.media.RINGER_MODE_CHANGED")
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED")
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED")
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED")
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED")
        intentFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED")
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE")
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")

        if (isApi33toHigher) {
            this.appContext?.registerReceiver(mInfoReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        } else {
            this.appContext?.registerReceiver(mInfoReceiver, intentFilter)
        }

        val telephonyManager = getSystemService("phone") as TelephonyManager
        val signalStrengthListener = SignalStrengthListener()
        telephonyManager.listen(signalStrengthListener, 256)

        val filter = IntentFilter()
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        wifiSignalStrengthReceiver = WifiSignalStrengthReceiver()
        if (isApi33toHigher) {
            registerReceiver(wifiSignalStrengthReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(wifiSignalStrengthReceiver, filter)
        }

        super.onServiceConnected()
    }

    var wifiSignalStrengthReceiver: WifiSignalStrengthReceiver? = null
    private lateinit var connectivityManager: ConnectivityManager


    override fun onDestroy() {
        try {
            this@StatusAccessibilityService.appContext?.let { context ->
                this.mBatInfoReceiver.let {
                    context.unregisterReceiver(it)
                }
                this.receiverRotateScreen?.let {
                    context.unregisterReceiver(it)
                }
                this.wifiSignalStrengthReceiver?.let {
                    context.unregisterReceiver(it)
                }
            }
            connectivityManager.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        SharedPreUtils.getInstance().unregisterListener(this@StatusAccessibilityService)
        super.onDestroy()
    }


    class ConfigurationChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "android.intent.action.CONFIGURATION_CHANGED") {
                val configuration = context.resources.configuration
                if (configuration.orientation == 2) {

                } else if (configuration.orientation == 1) {

                }
            }
        }
    }

    private fun switchAction(action: Int) {
        if (PrefManager.isEnableVibrate) {
            myVibrate()
        }
        if (action <= 0) return
        when (action) {
            1 -> backAction()
            2 -> homeAction()
            3 -> screenShot()
            4 -> recentAction()
            5 -> lockScreen()
            6 -> showPowerOptions()
            7 -> showQuickSettings()
            8 -> showNotification()
            9 -> {
                handler.postDelayed({swipe()},50)
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    fun swipe(
        onCompleted: (() -> Unit)? = null,
        onCancelled: (() -> Unit)? = null
    ): Boolean {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealMetrics(dm)
        val h = dm.heightPixels.toFloat()
        val path = Path().apply {
            moveTo(300f, h* 0.8f)
            lineTo(300f, h * 0.2f)
        }
        val stroke = StrokeDescription(
            path,
            0,
            50
        )
        val gesture = GestureDescription.Builder()
            .addStroke(stroke).build()
        Log.d(TAG, "swipe: start")
        return dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                Log.d(TAG, "swipe: onCompleted: ")
                onCompleted?.invoke()
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                Log.d(TAG, "swipe: onCancelled: ")
                onCancelled?.invoke()
            }
        }, null)
    }

    fun applyTemplate() {
        val templateValue = PrefManager.templateValue
        val templateModel = templateValue.fromJson(ColorTemplateModel::class.java)
        templateModel?.let { tp ->
            if (PrefManager.isUseTemplate) {
                binding.backgroundImage.visible()
                binding.backgroundStt.setBackgroundColor("#00000000".toColorInt())
                Glide.with(this).load(tp.url).into(binding.elementHome)

                PrefManager.iconColor = tp.hexColor
                PrefManager.pinDataId = 0

                PrefManager.emojiSize = 30
                pinGsColor = tp.hexColor
                pinGeColor = tp.hexColor

                appContext?.let {
                    Glide.with(it).load(PrefManager.batterySelectedIndex).into(binding.imagePin)
                }
            }
        }

    }


    private fun stateStatus(value: Boolean) {
        if (value) {
            binding.rootLayout.visible()
        } else {
            binding.rootLayout.gone()
        }
    }

    private fun showNotch(t: Int) {
        val layoutParams = binding.imgNotch.layoutParams as ConstraintLayout.LayoutParams

        if (t == -1) {
            binding.layoutNotch.gone()
        } else {
            binding.layoutNotch.visible()
            val notchResource = when (t) {
                1 -> R.drawable.notch_data_01
                2 -> R.drawable.notch_data_02
                3 -> R.drawable.notch_data_03
                4 -> R.drawable.notch_data_04
                5 -> R.drawable.notch_data_05
                6 -> R.drawable.notch_data_06
                7 -> R.drawable.notch_data_07
                8 -> R.drawable.notch_data_08
                9 -> R.drawable.notch_data_09
                10 -> R.drawable.notch_data_10
                11 -> R.drawable.notch_data_11
                12 -> R.drawable.notch_data_12
                13 -> R.drawable.notch_data_13
                14 -> R.drawable.notch_data_14
                15 -> R.drawable.notch_data_15
                else -> R.drawable.notch_data_00
            }

            binding.imgNotch.setImageResource(notchResource)
            layoutParams.verticalBias = if (t >= 8) 0.5f else 0f
        }
        binding.imgNotch.layoutParams.height = (statusBarHeight * 0.55f).toInt()
        binding.imgNotch.layoutParams = layoutParams
    }

    private fun StatusBarViewBinding.loadData(context: Context) {
        loadTextTime()
        loadTextDate()
        leftMargin.layoutParams.apply {
            width = PrefManager.leftMargin.dpToPx().roundToInt()
        }
        rightMargin.layoutParams.apply {
            width = PrefManager.rightMargin.dpToPx().roundToInt()
        }
        stateStatus(PrefManager.isShowStatusBar)
        showNotch(PrefManager.isShowNotch)

        loadPinData()
        loadPinDefault()
        loadEmojiData()
        loadViewPin()
        if (PrefManager.isShowAnimation) {
            loadAnimation()
        }


        if (PrefManager.isUseTemplate) {
            applyTemplate()
        }
        loadDefData()
        loadDefSignal()
        airPlaneMode.change {
            loadAriPlane(it)
        }
        if (PrefManager.isShowEmotion) {
            if (PrefManager.emotionPath != "") {
                appContext?.let {
                    Glide.with(it).load(PrefManager.emotionPath).into(binding.icEmotion)
                }
            } else {
                binding.icEmotion.gone()
            }
        }
        isHotspotEnabled.change {
            loadHotspot(it)
        }
        handleRingerModeChanged(context, object : RingerListener {
            override fun onModeDefault() {
                super.onModeDefault()
                binding.icRinger.gone()
            }

            override fun onModeNormal() {
                super.onModeNormal()
                binding.icRinger.visible()
                loadRinger(R.drawable.ic_ringer_default)
            }

            override fun onModeSilent() {
                super.onModeSilent()
                binding.icRinger.visible()
                loadRinger(R.drawable.ic_ringer_off)
            }

            override fun onModeVibrate() {
                super.onModeVibrate()
                binding.icRinger.visible()
                loadRinger(R.drawable.ic_ringer_vibrate)
            }
        })

        binding.icRinger.setColorFilter(PrefManager.ringerColor.toColorInt())
        binding.icHosPost.setColorFilter(PrefManager.hotspotColor.toColorInt())
        binding.icAirPlane.setColorFilter(PrefManager.airplaneColor.toColorInt())
        binding.icSignal.setColorFilter(PrefManager.signalColor.toColorInt())
        binding.icData.setColorFilter(PrefManager.dataColor.toColorInt())
        binding.icWifi.setColorFilter(PrefManager.wifiColor.toColorInt())
    }

    fun loadRinger(icon: Int) {
        binding.icRinger.setImageResource(icon)
        binding.icRinger.layoutParams.width = PrefManager.ringerSize.scaleResource()
        binding.icRinger.setColorFilter(PrefManager.ringerColor.toColorInt())
        binding.icRinger.requestLayout()
    }

    private fun loadHotspot(it: Boolean) {
        binding.icHosPost.showState(it)
        binding.icHosPost.layoutParams.width = PrefManager.hotspotSize.scaleResource()
        binding.icHosPost.setColorFilter(PrefManager.hotspotColor.toColorInt())
        binding.icHosPost.requestLayout()
    }


    private fun loadAriPlane(isAirplaneModeOn: Boolean) {
        binding.icAirPlane.showState(isAirplaneModeOn)
        binding.icSignal.showState(!isAirplaneModeOn)
        binding.icData.showState(!isAirplaneModeOn)

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        when {
            // Không có mạng
            activeNetwork == null || !activeNetwork.isConnected -> {
                binding.icWifi.gone()
            }

            // Wi-Fi đang kết nối
            activeNetwork.type == ConnectivityManager.TYPE_WIFI -> {
                binding.icWifi.visible()
            }
        }

        if (isHotspotEnabled() == true) {
            binding.icHosPost.visible()
        } else {
            binding.icHosPost.gone()
        }

        binding.icAirPlane.layoutParams.width = PrefManager.airplaneSize.scaleResource()
        binding.icAirPlane.setColorFilter(PrefManager.airplaneColor.toColorInt())
        binding.icAirPlane.requestLayout()
    }

    private fun isNetworkActive(state: NetworkInfo.State?): Boolean {
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING
    }

    private fun updateSongStyle0(level: Int) {
        binding.icSignal.setImageResource(
            when (level) {
                2 -> R.drawable.ic_sign_dt_2
                3 -> R.drawable.ic_sign_dt_3
                4 -> R.drawable.ic_sign_dt_4
                else -> R.drawable.ic_sign_dt_1
            }
        )
        binding.icSignal.setColorFilter(PrefManager.signalColor.toColorInt())
    }

    private inner class SignalStrengthListener : PhoneStateListener() {
        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            updateSongStyle0(signalStrength.level)
        }

    }

    private fun updateUIWithSignalStrength(signalLevel: Int) {
        when (signalLevel) {
            0 -> {
                binding.icWifi.setImageResource(R.drawable.ic_wifi_data_1)
            }

            1 -> {
                binding.icWifi.setImageResource(R.drawable.ic_wifi_data_2)
            }

            2 -> {
                binding.icWifi.setImageResource(R.drawable.ic_wifi_data_3)
            }
        }
        binding.icWifi.requestLayout()
    }

    inner class WifiSignalStrengthReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (WifiManager.RSSI_CHANGED_ACTION == action) {
                val wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
                val rssi = wifiManager.connectionInfo.rssi
                val signalLevel = WifiManager.calculateSignalLevel(
                    rssi, 2
                )
                updateUIWithSignalStrength(signalLevel)
            }
        }


    }

    private fun loadDefData() {
        binding.icData.layoutParams.width = PrefManager.dataSize.scaleResource()
        binding.icData.requestLayout()
        when (PrefManager.dataInt) {
            0 -> binding.icData.setImageResource(R.drawable.ic_sign_2g)
            1 -> binding.icData.setImageResource(R.drawable.ic_sign_3g)
            2 -> binding.icData.setImageResource(R.drawable.ic_sign_4g)
            3 -> binding.icData.setImageResource(R.drawable.ic_sign_5g)
            4 -> binding.icData.setImageResource(R.drawable.ic_sign_6g)
            5 -> binding.icData.setImageResource(R.drawable.ic_sign_lite)
            else -> binding.icData.setImageResource(R.drawable.ic_sign_4g)
        }
        binding.icSignal.setColorFilter(PrefManager.signalColor.toColorInt())

    }

    fun loadDefSignal() {
        binding.icSignal.layoutParams.width = PrefManager.signalSize.scaleResource()

        binding.icSignal.setColorFilter(PrefManager.signalColor.toColorInt())
        binding.icSignal.requestLayout()
    }


    fun Int.scaleResource(): Int {
        return (this * resources.displayMetrics.scaledDensity).toInt()
    }


    fun Float.scaleResource(): Int {
        return (this * resources.displayMetrics.scaledDensity).toInt()
    }

    private fun loadEmojiData() {
        binding.viewShowPin.showState(PrefManager.isShowEmoji)
        binding.viewPinDefault.showState(!PrefManager.isShowEmoji)

        if (PrefManager.emojiSelectedIndex != "") {
            appContext?.let {
                Glide.with(it).load(PrefManager.emojiSelectedIndex).into(binding.imageEmoji)
            }
        }

        binding.imageEmoji.requestLayout()
        binding.imageEmoji.layoutParams.width = PrefManager.emojiSize + 20
    }

    private fun loadPinData() {
        binding.textPin.showState(PrefManager.isShowBatteryPercentage)
        binding.textPinCharging.showState(PrefManager.isShowBatteryPercentage)

        binding.textPin.apply {
            textSize = PrefManager.batteryPercentageTextSize.toFloat()
            setTextColor(PrefManager.batteryPercentageColor.toColorInt())
        }
        binding.textPinCharging.apply {
            setTextColor(PrefManager.batteryPercentageColor.toColorInt())
        }
        appContext?.let {
            Glide.with(it).load(PrefManager.batterySelectedIndex).into(binding.imagePin)
        }
        binding.imagePin.layoutParams.width = PrefManager.emojiSize + 20
        binding.imagePin.requestLayout()
    }

    private fun loadPinDefault() {
        binding.viewShowPin.showState(PrefManager.isShowEmoji)
        binding.viewPinDefault.showState(!PrefManager.isShowEmoji)

        binding.tvPinDefault.setTextColor(PrefManager.batteryDefaultPercentageColor.toColorInt())
        binding.tvPinDefault.textSize = PrefManager.batteryPercentageTextSize.toFloat()

        binding.ivPinDefault.imageTintList =
            ColorStateList.valueOf(PrefManager.batteryDefaultBackgroundColor.toColorInt())
        binding.ivPinDefault.layoutParams.width = PrefManager.emojiSize + 20
        binding.ivPinDefault.requestLayout()
    }


    private fun loadViewPin() {
        appContext?.let {
            Glide.with(it).load(PrefManager.batterySelectedIndex).into(binding.imagePin)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onPreferenceChanged(key: String) {
        Log.d(TAG, "Data Show : $key")
        when (key) {
            PrefManager.KEY_EMOJI_SIZE, PrefManager.KEY_SHOW_EMOJI, PrefManager.KEY_EMOJI_SELECTED_INDEX -> {
                loadEmojiData()
                loadPinData()
                loadPinDefault()
            }

            PrefManager.KEY_BATTERY_SIZE -> {
                loadPinData()
                loadPinDefault()
            }

            PrefManager.KEY_USE_TEMPLATE -> {
                applyTemplate()
            }

            PrefManager.KEY_PIN_GE_COLOR, PrefManager.KEY_PIN_GS_COLOR -> {
                loadViewPin()
                binding.chargingValue.setChargingColor(
                    pinGsColor, pinGeColor
                )
            }

            PrefManager.KEY_BATTERY_SELECTED_INDEX -> {
                appContext?.let {
                    Glide.with(it).load(PrefManager.batterySelectedIndex).into(binding.imagePin)
                }
            }

            PrefManager.KEY_PIN_DATA_ID -> {
                loadViewPin()
            }

            PrefManager.KEY_DATA_SIZE, PrefManager.KEY_DATA_INDEX, PrefManager.KEY_DATA_COLOR -> {
                loadDefData()
            }

            PrefManager.KEY_SIGNAL_SIZE, PrefManager.KEY_SIGNAL_COLOR -> {
                loadDefSignal()
            }


            PrefManager.KEY_SHOW_BATTERY_PERCENTAGE -> {
                binding.textPin.showState(PrefManager.isShowBatteryPercentage)
                binding.textPinCharging.showState(PrefManager.isShowBatteryPercentage)

            }


            PrefManager.KEY_AIRPLANE_COLOR, PrefManager.KEY_AIRPLANE_SIZE -> {
                loadAriPlane(airPlaneMode.value!!)
            }

            PrefManager.KEY_HOTSPOT_SIZE, PrefManager.KEY_HOTSPOT_COLOR -> {
                loadHotspot(isHotspotEnabled.value!!)
            }

            PrefManager.KEY_RINGER_SIZE, PrefManager.KEY_RINGER_COLOR -> {
                appContext?.let {
                    handleRingerModeChanged(it, object : RingerListener {
                        override fun onModeDefault() {
                            super.onModeDefault()
                            binding.icRinger.gone()
                        }

                        override fun onModeNormal() {
                            super.onModeNormal()
                            binding.icRinger.visible()
                            loadRinger(R.drawable.ic_ringer_default)
                        }

                        override fun onModeSilent() {
                            super.onModeSilent()
                            binding.icRinger.visible()
                            loadRinger(R.drawable.ic_ringer_off)
                        }

                        override fun onModeVibrate() {
                            super.onModeVibrate()
                            binding.icRinger.visible()
                            loadRinger(R.drawable.ic_ringer_vibrate)
                        }
                    })
                }

            }

            PrefManager.KEY_WIFI_SIZE -> {
                binding.icWifi.layoutParams.width = PrefManager.wifiSize.scaleResource()
                binding.icWifi.requestLayout()
            }

            PrefManager.KEY_WIFI_COLOR -> {
                binding.icWifi.setColorFilter(PrefManager.wifiColor.toColorInt())
                binding.icWifi.requestLayout()
            }

            PrefManager.KEY_BATTERY_PERCENTAGE_COLOR -> {
                binding.textPin.setTextColor(PrefManager.batteryPercentageColor.toColorInt())
                binding.textPinCharging.setTextColor(PrefManager.batteryPercentageColor.toColorInt())
            }

            PrefManager.KEY_BATTERY_DEFAULT_PERCENTAGE_COLOR -> {
                binding.tvPinDefault.setTextColor(PrefManager.batteryDefaultPercentageColor.toColorInt())
            }

            PrefManager.KEY_BATTERY_DEFAULT_BACKGROUND_COLOR -> {
                binding.ivPinDefault.imageTintList =
                    ColorStateList.valueOf(PrefManager.batteryDefaultBackgroundColor.toColorInt())
            }

            PrefManager.KEY_BATTERY_PERCENTAGE_SIZE -> {
                binding.textPin.textSize = PrefManager.batteryPercentageTextSize.toFloat()
                binding.tvPinDefault.textSize = PrefManager.batteryPercentageTextSize.toFloat()
            }

            PrefManager.KEY_SHOW_ANIMATION -> {
                binding.ltAnimation.showState(PrefManager.isShowAnimation)
                if (PrefManager.isShowAnimation) {
                    loadAnimation()
                }
            }

            PrefManager.KEY_ANIMATION_SIZE -> {
                if (PrefManager.isShowAnimation) {
                    binding.ltAnimation.layoutParams.width = PrefManager.animationSize + 30
                    binding.ltAnimation.layoutParams.height = PrefManager.animationSize + 30
                    binding.ltAnimation.requestLayout()
                    binding.ltAnimation.playAnimation()
                }
            }

            PrefManager.KEY_ANIMATION_ID -> {
                loadAnimation()
            }

            PrefManager.KEY_ANIMATION_URL -> {
//                loadAnimationFromUrl()
            }


            PrefManager.KEY_BATTERY_CHARGE_ICON -> {
                binding.chargingValue.setChargingEmoji(PrefManager.batteryChargeIcon)
            }

            PrefManager.KEY_STATUS_BAR_HEIGHT -> {
                val newH =
                    (PrefManager.statusBarHeight * resources.displayMetrics.scaledDensity).toInt()
                binding.rootLayout.layoutParams?.let { params ->
                    params.height = newH
                    binding.root.layoutParams = params
                    if (newH < statusBarHeight) {
                        binding.imgNotch.layoutParams.height = (newH * 0.55f).toInt()
                        binding.imgNotch.requestLayout()
                    } else {
                        binding.imgNotch.layoutParams.height = (statusBarHeight * 0.55f).toInt()
                        binding.imgNotch.requestLayout()
                    }
                    binding.root.requestLayout() // Request a layout update
                    binding.root.invalidate()
                } ?: run {
                    Log.e(TAG, "LayoutParams is null!")
                }
            }

            PrefManager.KEY_SHOW_EMOTION, PrefManager.KEY_EMOTION_PATH -> {
                binding.icEmotion.showState(PrefManager.isShowEmotion)
                if (PrefManager.isShowEmotion) {
                    if (PrefManager.emotionPath != "") {
                        appContext?.let {
                            Glide.with(it).load(PrefManager.emotionPath).into(binding.icEmotion)
                        }
                    }
                }
            }


            PrefManager.KEY_IS_SHOW_STATUS -> {
                stateStatus(PrefManager.isShowStatusBar)
            }

            PrefManager.KEY_ICON_COLOR -> {

                pinGsColor = PrefManager.iconColor
                pinGeColor = PrefManager.iconColor
                PrefManager.batteryPercentageColor = PrefManager.iconColor
                PrefManager.batteryDefaultPercentageColor = PrefManager.iconColor
                PrefManager.wifiColor = PrefManager.iconColor
                PrefManager.dataColor = PrefManager.iconColor
                PrefManager.signalColor = PrefManager.iconColor
                PrefManager.airplaneColor = PrefManager.iconColor
                PrefManager.hotspotColor = PrefManager.iconColor
                PrefManager.ringerColor = PrefManager.iconColor
                PrefManager.timeTextColor = PrefManager.iconColor
                PrefManager.dateColor = PrefManager.iconColor
            }

            PrefManager.KEY_BACKGROUND_COLOR -> {
                binding.backgroundImage.gone()
                binding.backgroundStt.setBackgroundColor(PrefManager.backGroundViewColor.toColorInt())
                binding.backgroundStt.visible()
                binding.elementHome.setImageBitmap(null)
            }

            PrefManager.KEY_LEFT_MARGIN -> {
                binding.leftMargin.layoutParams.apply {
                    width = (PrefManager.leftMargin + 10).dpToPx().roundToInt()
                }
                binding.leftMargin.requestLayout()
            }

            PrefManager.KEY_RIGHT_MARGIN -> {
                binding.rightMargin.layoutParams.apply {
                    width = (PrefManager.rightMargin + 10).dpToPx().roundToInt()
                }
                binding.rightMargin.requestLayout()
            }

            PrefManager.KEY_SHOW_TIME, PrefManager.KEY_TIME_COLOR, PrefManager.KEY_TIME_TEXT_SIZE, PrefManager.KEY_TIME_APM, PrefManager.KEY_TIME_SHOW_SECOND -> {
                loadTextTime()
            }

            PrefManager.KEY_SHOW_DATE, PrefManager.KEY_DATE_SIZE, PrefManager.KEY_DATE_COLOR, PrefManager.KEY_DATE_FORMAT -> {
                loadTextDate()
            }

            PrefManager.KEY_SHOW_NOTCH -> {
                showNotch(PrefManager.isShowNotch)
            }


            PrefManager.KEY_ENABLE_GESTURE -> {
                localLayoutParams = layoutParams.apply {
                    flags =
                        if (PrefManager.isEnableGesture) this@StatusAccessibilityService.flagNormal else this@StatusAccessibilityService.flagNormal or 16
                }
                manager!!.updateViewLayout(binding.root, this.localLayoutParams)
                binding.llAction.showState(PrefManager.isEnableGesture)
            }
        }
    }

    private fun getAnimation(): Int {
        return when (PrefManager.animationId) {
            1 -> R.raw.animation_01
            2 -> R.raw.animation_02
            3 -> R.raw.animation_03
            4 -> R.raw.animation_04
            5 -> R.raw.animation_05
            6 -> R.raw.animation_06
            7 -> R.raw.animation_07
            8 -> R.raw.animation_08
            9 -> R.raw.animation_09
            10 -> R.raw.animation_10
            11 -> R.raw.animation_11
            12 -> R.raw.animation_12
            13 -> R.raw.animation_13
            14 -> R.raw.animation_14
            15 -> R.raw.animation_15
            else -> R.raw.animation_01
        }
    }

    private fun loadAnimation() {
        if (PrefManager.animationURL.startsWith("http")) {
            loadAnimationFromUrl()
        } else {
            loadFallbackAnimation()
        }
    }

    private fun loadAnimationFromUrl() {
        Thread {
            try {
                val url = PrefManager.animationURL
                val connection = URL(url).openConnection()
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.setRequestProperty("User-Agent", "Android App")

                val responseCode = (connection as HttpURLConnection).responseCode
                Log.d(TAG, "Response code: $responseCode")

                if (responseCode == 200) {
                    val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d(TAG, "JSON downloaded, size: ${jsonString.length}")

                    Handler(Looper.getMainLooper()).post {
                        LottieCompositionFactory.fromJsonString(jsonString, url)
                            .addListener { composition ->
                                Log.d(TAG, "Animation parsed successfully")
                                binding.ltAnimation.setComposition(composition)
                                binding.ltAnimation.layoutParams.width =
                                    PrefManager.animationSize + 30
                                binding.ltAnimation.layoutParams.height =
                                    PrefManager.animationSize + 30
                                binding.ltAnimation.requestLayout()
                                binding.ltAnimation.playAnimation()
                            }.addFailureListener { exception ->
                                Log.e(TAG, "Failed to parse JSON", exception)
                                loadFallbackAnimation()
                            }
                    }
                } else {
                    Log.e(TAG, "HTTP error: $responseCode")
                    Handler(Looper.getMainLooper()).post {
                        loadFallbackAnimation()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error: ${e.message}")
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    loadFallbackAnimation()
                }
            }
        }.start()
    }

    private fun loadFallbackAnimation() {
        try {
            binding.ltAnimation.visible()
            binding.ltAnimation.setAnimation(getAnimation())
            binding.ltAnimation.layoutParams.width = PrefManager.animationSize + 30
            binding.ltAnimation.layoutParams.height = PrefManager.animationSize + 30
            binding.ltAnimation.playAnimation()
            binding.ltAnimation.requestLayout()
        } catch (e: Exception) {
            Log.e(TAG, "Fallback animation failed", e)
        }
    }

    private fun loadTextDate() {
        binding.textDate.showState(PrefManager.isShowDate)
        binding.textDate.setTextColor(PrefManager.dateColor.toColorInt())
        binding.textDate.textSize = PrefManager.dateSize.toFloat()
        val listItemTemplate = listDateTemplate()
        listItemTemplate.find { it.index == PrefManager.dateFormatIndex }?.let {
            binding.textDate.text = styleDateModel(this@StatusAccessibilityService, it)
        }
    }

    private fun loadTextTime() {
        binding.elementTime.showState(PrefManager.isShowTime)
        binding.elementTime.setTextColor(PrefManager.timeTextColor.toColorInt())
        binding.elementTime.textSize = PrefManager.timeTextSize.toFloat()
        if (PrefManager.isTimeShowAmPm) {
            if (PrefManager.timeShowSecond) {
                binding.elementTime.format12Hour = "h:mm:ss a"
                binding.elementTime.format24Hour = "HH:mm:ss"
            } else {
                binding.elementTime.format12Hour = "h:mm a"
                binding.elementTime.format24Hour = "HH:mm"
            }
        } else if (PrefManager.timeShowSecond) {
            binding.elementTime.format12Hour = "h:mm:ss"
            binding.elementTime.format24Hour = "HH:mm:ss"
        } else {
            binding.elementTime.format12Hour = "h:mm"
            binding.elementTime.format24Hour = "HH:mm"
        }
    }

    fun showNotification() {
        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS)
    }

    fun showQuickSettings() {
        performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS)
    }

    fun lockScreen() {
        performGlobalAction(8)
    }

    fun showPowerOptions() {
        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
    }


    fun backAction() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    fun homeAction() {
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    fun recentAction() {
        performGlobalAction(GLOBAL_ACTION_RECENTS)
    }

    fun screenShot() {
        performGlobalAction(9)
    }

    fun myVibrate() {
        val vibrator = getSystemService("vibrator") as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    PrefManager.vibrateDuration.toLong(), -1
                )
            )
        } else {
            vibrator.vibrate(PrefManager.vibrateDuration.toLong())
        }
    }


    fun playTap(i: Int, i2: Int) {
        val path = Path()
        path.moveTo(300.0f, i.toFloat())
        path.lineTo(300.0f, i2.toFloat())
        val builder = GestureDescription.Builder()
        builder.addStroke(StrokeDescription(path, 0L, 50L))
        dispatchGesture(builder.build(), object : GestureResultCallback() {

        }, null)
    }

    private var mX = 0
    private var mY = 0
    private var mRunnable: IntervalRunnable? = null
    private val mHandler: Handler? = null
    private var screenHeight = 0

    inner class IntervalRunnable : Runnable {
        override fun run() {
            playTap(
                mX, mY
            )
        }
    }

    fun swipeUp() {
        if (this.mRunnable == null) {
            this.mRunnable = IntervalRunnable()
        }
        val i: Int = this.screenHeight
        this.mX = i / 5
        this.mY = (i * 4) / 5
        mRunnable?.let {
            mHandler?.postDelayed(this.mRunnable!!, 5L)
        }

    }

    fun swipeDown() {
        if (this.mRunnable == null) {
            this.mRunnable = IntervalRunnable()
        }
        val i: Int = this.screenHeight
        this.mX = (i * 4) / 5
        this.mY = i / 5
        mRunnable?.let {
            mHandler?.postDelayed(this.mRunnable!!, 5L)
        }
    }

    private fun getLauncherPackageName(context: Context): String? {
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.HOME")
        val resolveActivity = context.packageManager.resolveActivity(intent, 65536)
        if (resolveActivity?.activityInfo == null) {
            return null
        }
        return resolveActivity.activityInfo.packageName
    }

}

