package com.example.basekotlin.model

import android.os.Parcelable
import com.example.basekotlin.R
import com.example.basekotlin.util.customview.battery.colorOpacity
import com.example.basekotlin.util.customview.battery.fromColor
import com.example.basekotlin.util.customview.battery.processColor
import kotlinx.parcelize.Parcelize


class TemplateBuilder {
    fun build(templates: Templates) {

    }
}

enum class BgType {
    COLOR, IMAGE
}

@Parcelize
data class StatusBackground(var resource: Int, var type: BgType) : Parcelable

@Parcelize
class DateTemplate(
    var day: String = "#000000", var month: String = "#000000", var year: String = "#000000"
) : Parcelable

@Parcelize
data class Templates(
    var templateId: Int,
    var background: StatusBackground,
    var pinColor: String = "",
    var timeColor: String = "000000",
    var dateTemplate: DateTemplate = DateTemplate(),
    var pinG1Color: String = "#000000",
    var pinG2Color: String = "#000000",
    var pinTextSize: Int = 10
) : Parcelable


fun svgPinTemplate(color1: String, color2: String): String {
    val cl1 = processColor(color1)
    val cl2 = processColor(color2)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="67" height="36" viewBox="0 0 67 36" fill="none">
        <path opacity="0.2" d="M0.54541 5.95041C0.54541 2.66409 3.2095 0 6.49582 0H54.595C57.8813 0 60.5454 2.66409 60.5454 5.95041V7.8C60.5454 8.46274 61.0827 9 61.7454 9V9C64.3964 9 66.5454 11.149 66.5454 13.8V22.2C66.5454 24.851 64.3964 27 61.7454 27V27C61.0827 27 60.5454 27.5373 60.5454 28.2V30.0496C60.5454 33.3359 57.8813 36 54.595 36H6.49582C3.2095 36 0.54541 33.3359 0.54541 30.0496V5.95041Z" fill="#$cl1"/>
        <rect x="3.54541" y="3" width="45" height="30" rx="6" fill="#$cl2"/>
        </svg>""".trimIndent()
}


fun svgWifiTemplate(color1: String, color2: String): String {
    val cl1 = processColor(color1)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="424" height="424" viewBox="0 0 424 424">
          <path
            d="M382.1,188.8c-4.6,4.6 -11.9,4.8 -10.7,0.5c-42.3,-38.4 -96.6,-59.3 -154.1,-59.3c-57.5,0 -111.8,21 -154.1,59.3c-4.8,4.3 -10.1,4.1 -10.7,-0.5l-30.2,-30.2c-4.9,-4.9 -4.8,-10.8 0.3,-17.5C57.5,98 115.9,71.5 178.6,64.7c10.8,-1.2 21.8,-1.8 32.8,-1.8c12.1,0 24.1,0.7 36,2.1C308.8,72.4 366,98.7 411.9,141c5.1,4.7 5.3,10.7 0.4,17.6L382.1,188.8z"
            fill="#$cl1" fill-opacity="0.2"/>
          <path
            d="M212.4,165.6c-50.7,0 -96.8,19.9 -131,52.2c-4.9,4.7 -5.1,10.4 -0.4,17.3l29.6,30.7c4.6,4.8 10.2,5 17,0.5c22.2,-20.9 52,-33.7 84.8,-33.7c32.6,0 62.3,10.7 84.4,33.3c4.8,4.5 10.4,4.2 17,-0.5l29.6,-30.7c4.7,-4.9 4.5,-10.7 -0.5,-17.3C308.8,185.3 262.9,165.6 212.4,165.6z"
            fill="#$cl1" />
          <path
            d="M213,268c-25,0 -47.2,10.4 -60.7,31.4c-2.3,3.2 -2,7.6 0.7,10.5l44.8,46.3l8.3,8.6c3.2,3.3 8.6,3.3 11.8,0l8.3,-8.6l45.9,-47.5c2.8,-2.9 3.1,-7.4 0.7,-10.6C259.1,279.8 237.4,268 213,268z"
            fill="#$cl1"  />
        </svg>
        """.trimIndent()
}

fun svgDataTemplate(color1: String, color2: String): String {
    val cl1 = processColor(color1)
    return """<svg width="26" height="20" viewBox="0 0 26 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M0 13C0 12.4477 0.447715 12 1 12H4C4.55228 12 5 12.4477 5 13V19C5 19.5523 4.55228 20 4 20H1C0.447715 20 0 19.5523 0 19V13Z" fill="#$cl1"/>
            <path d="M7 9C7 8.44772 7.44772 8 8 8H11C11.5523 8 12 8.44772 12 9V19C12 19.5523 11.5523 20 11 20H8C7.44772 20 7 19.5523 7 19V9Z" fill="#$cl1"/>
            <path d="M14 5C14 4.44772 14.4477 4 15 4H18C18.5523 4 19 4.44772 19 5V19C19 19.5523 18.5523 20 18 20H15C14.4477 20 14 19.5523 14 19V5Z" fill="#$cl1"/>
            <path d="M21 1C21 0.447716 21.4477 0 22 0H25C25.5523 0 26 0.447715 26 1V19C26 19.5523 25.5523 20 25 20H22C21.4477 20 21 19.5523 21 19V1Z" fill="#$cl1" fill-opacity="0.2"/>
            </svg>
            """.trimIndent()
}
