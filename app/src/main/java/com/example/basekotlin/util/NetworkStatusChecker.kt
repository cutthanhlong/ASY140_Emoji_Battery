package com.example.basekotlin.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import java.lang.reflect.Method

/**
 * Utility class để kiểm tra trạng thái các kết nối mạng
 */
class NetworkStatusChecker(private val context: Context) {

    companion object {
        private const val TAG = "NetworkStatusChecker"
    }

    /**
     * Kiểm tra WiFi có đang bật không
     * @return true nếu WiFi đang bật, false nếu tắt
     */
    fun isWifiEnabled(): Boolean {
        return try {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiManager.isWifiEnabled
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking WiFi status: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra WiFi có đang kết nối không
     * @return true nếu đang kết nối WiFi, false nếu không
     */
    fun isWifiConnected(): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking WiFi connection: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra chế độ máy bay có đang bật không
     * @return true nếu airplane mode đang bật, false nếu tắt
     */
    fun isAirplaneModeEnabled(): Boolean {
        return try {
            Settings.Global.getInt(
                context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking airplane mode: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra hotspot (tethering) có đang bật không
     * Cần quyền ACCESS_WIFI_STATE
     * @return true nếu hotspot đang bật, false nếu tắt hoặc không xác định được
     */
    fun isHotspotEnabled(): Boolean {
        return try {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

            // Cách 1: Sử dung reflection (có thể không hoạt động trên một số thiết bị mới)
            try {
                val method: Method = wifiManager.javaClass.getDeclaredMethod("isWifiApEnabled")
                method.isAccessible = true
                method.invoke(wifiManager) as Boolean
            } catch (e: Exception) {
                // Cách 2: Kiểm tra qua ConnectivityManager (Android 6.0+)
                checkHotspotViaConnectivityManager()
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking hotspot status: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra hotspot qua ConnectivityManager (Android 6.0+)
     */
    private fun checkHotspotViaConnectivityManager(): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networks = connectivityManager.allNetworks

            networks.any { network ->
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_WIFI_P2P) == true
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking hotspot via ConnectivityManager: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra mạng di động (cellular) có đang bật không
     * Cần quyền READ_PHONE_STATE hoặc ACCESS_NETWORK_STATE
     * @return true nếu mạng di động đang bật, false nếu tắt
     */
    fun isMobileDataEnabled(): Boolean {
        return try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Android 8.0+ - Cần quyền READ_PHONE_STATE
                if (ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    telephonyManager.isDataEnabled
                } else {
                    // Fallback: kiểm tra qua ConnectivityManager
                    isMobileDataConnected()
                }
            } else {
                // Android 7.1 và thấp hơn - sử dụng reflection
                try {
                    val method = telephonyManager.javaClass.getDeclaredMethod("getDataEnabled")
                    method.isAccessible = true
                    method.invoke(telephonyManager) as Boolean
                } catch (e: Exception) {
                    // Fallback
                    isMobileDataConnected()
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking mobile data status: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra mạng di động có đang kết nối không
     * @return true nếu đang kết nối qua mạng di động, false nếu không
     */
    fun isMobileDataConnected(): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking mobile data connection: ${e.message}")
            false
        }
    }

    /**
     * Kiểm tra có kết nối internet không (bất kể WiFi hay mobile data)
     * @return true nếu có kết nối internet, false nếu không
     */
    fun isInternetAvailable(): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error checking internet availability: ${e.message}")
            false
        }
    }

    /**
     * Lấy thông tin chi tiết về trạng thái mạng
     * @return NetworkStatusInfo object chứa tất cả thông tin
     */
    fun getNetworkStatusInfo(): NetworkStatusInfo {
        return NetworkStatusInfo(
            isWifiEnabled = isWifiEnabled(),
            isWifiConnected = isWifiConnected(),
            isAirplaneModeEnabled = isAirplaneModeEnabled(),
            isHotspotEnabled = isHotspotEnabled(),
            isMobileDataEnabled = isMobileDataEnabled(),
            isMobileDataConnected = isMobileDataConnected(),
            isInternetAvailable = isInternetAvailable()
        )
    }

    /**
     * In ra log tất cả trạng thái mạng
     */
    fun logAllNetworkStatus() {
        val info = getNetworkStatusInfo()
        android.util.Log.d(TAG, "=== Network Status ===")
        android.util.Log.d(TAG, "WiFi Enabled: ${info.isWifiEnabled}")
        android.util.Log.d(TAG, "WiFi Connected: ${info.isWifiConnected}")
        android.util.Log.d(TAG, "Airplane Mode: ${info.isAirplaneModeEnabled}")
        android.util.Log.d(TAG, "Hotspot Enabled: ${info.isHotspotEnabled}")
        android.util.Log.d(TAG, "Mobile Data Enabled: ${info.isMobileDataEnabled}")
        android.util.Log.d(TAG, "Mobile Data Connected: ${info.isMobileDataConnected}")
        android.util.Log.d(TAG, "Internet Available: ${info.isInternetAvailable}")
        android.util.Log.d(TAG, "====================")
    }
}

/**
 * Data class để chứa thông tin trạng thái mạng
 */
data class NetworkStatusInfo(
    val isWifiEnabled: Boolean,
    val isWifiConnected: Boolean,
    val isAirplaneModeEnabled: Boolean,
    val isHotspotEnabled: Boolean,
    val isMobileDataEnabled: Boolean,
    val isMobileDataConnected: Boolean,
    val isInternetAvailable: Boolean
)

/**
 * Extension functions để dễ sử dụng
 */
fun Context.isWifiEnabled(): Boolean = NetworkStatusChecker(this).isWifiEnabled()
fun Context.isWifiConnected(): Boolean = NetworkStatusChecker(this).isWifiConnected()
fun Context.isAirplaneModeEnabled(): Boolean = NetworkStatusChecker(this).isAirplaneModeEnabled()
fun Context.isHotspotEnabled(): Boolean = NetworkStatusChecker(this).isHotspotEnabled()
fun Context.isMobileDataEnabled(): Boolean = NetworkStatusChecker(this).isMobileDataEnabled()
fun Context.isMobileDataConnected(): Boolean = NetworkStatusChecker(this).isMobileDataConnected()
fun Context.isInternetAvailable(): Boolean = NetworkStatusChecker(this).isInternetAvailable()