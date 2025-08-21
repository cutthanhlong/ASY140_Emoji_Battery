package com.example.basekotlin.util.customview.battery

fun processColor(color: String): String {
    val cleanedColor = color.trim().replace("#", "")
    return if (cleanedColor.length == 8) {
        cleanedColor.substring(2) // Remove the alpha component
    } else {
        cleanedColor
    }
}


fun wifiSvg0(
    col1: String = "#FF0000", process: Int = 60
): String {
    val process1 = if (process > 85) 1 else 0.25
    val process2 = if (process in 40..85) 1 else 0.25
    return """<svg xmlns="http://www.w3.org/2000/svg" width="63" height="55" viewBox="0 0 63 55" fill="none">
<path opacity="$process1" d="M31.8431 0C20.354 0 9.03318 4.19331 0.378039 11.4977C-0.0398208 11.8504 -0.122781 12.5339 0.186681 13.0152L4.99331 19.5695C5.30317 20.0514 5.90197 20.1367 6.32127 19.785C13.489 13.7737 22.312 10.2157 31.8431 10.2157C41.3742 10.2157 49.5897 13.4848 56.7575 19.4961C57.1768 19.8478 57.7625 19.7515 58.0723 19.2696L62.8133 13.1301C63.1227 12.6488 63.0398 11.9653 62.6219 11.6127C53.9668 4.30825 43.3322 0 31.8431 0Z" fill="#$col1"/>
<path  opacity="${process2}" d="M10.4239 24.472C9.95101 24.85 9.86031 25.6704 10.2199 26.2136L14.7226 32.5728C15.0311 33.0388 15.7993 32.8965 16.2053 32.5728C20.6022 29.0674 25.4476 27.245 31.2529 27.245C37.0582 27.245 42.4294 29.3037 46.8263 32.8091C47.2323 33.1328 47.7668 33.0388 48.0753 32.5728L52.7801 26.2136C53.1397 25.6704 53.049 24.85 52.5761 24.472C46.6758 19.7561 39.0043 17 31.2529 17C23.5015 17 16.3242 19.7561 10.4239 24.472Z" fill="#$col1"/>
<path  opacity="1" d="M31.9291 54.5874C31.4253 55.1375 30.4967 55.1375 29.9929 54.5874L19.2908 39.4149C18.8131 38.8933 18.9341 38.1077 19.5786 37.7655C22.8609 36.0231 26.7917 35 31 35C35.2083 35 39.1391 36.0231 42.4214 37.7655C43.0659 38.1077 43.1869 38.8933 42.7092 39.4149L31.9291 54.5874Z" fill="#$col1"/>
</svg>""".trimIndent()
}

fun wifiSvg1(col1: String, col2: String): String {
    val color1 = processColor(col1)
    val color2 = processColor(col2)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="8" height="6" viewBox="0 0 8 6" fill="none">
            <path d="M0.423758 1.71146L3.53782 5.44835C3.7777 5.7362 4.21981 5.7362 4.45969 5.44835L7.57376 1.71146C7.80121 1.43852 7.74366 1.02674 7.43114 0.857727C6.42967 0.31612 5.25306 0.00146484 3.99875 0.00146484C2.74445 0.00146484 1.56784 0.31612 0.56637 0.857727C0.253855 1.02674 0.196307 1.43852 0.423758 1.71146Z" fill="url(#paint0_linear_227_17687)"/>
            <defs>
            <linearGradient id="paint0_linear_227_17687" x1="9" y1="44" x2="9" y2="56" gradientUnits="userSpaceOnUse">
            <stop stop-color="#$color1"/>
            <stop offset="1" stop-color="#$color2"/>
            </linearGradient>
            </defs>
            </svg>""".trimIndent()
}

fun wifiSvg2(col1: String, col2: String): String {
    val color1 = processColor(col1)
    val color2 = processColor(col2)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="16" height="5" viewBox="0 0 16 5" fill="none">
            <path d="M0.848076 2.03336C0.544485 2.21757 0.487008 2.62802 0.71434 2.90082L1.9418 4.37376C2.13924 4.6107 2.48319 4.65764 2.74747 4.49865C4.33016 3.54651 6.23624 2.99121 8.28967 2.99121C10.3431 2.99121 12.2492 3.54651 13.8319 4.49865C14.0961 4.65764 14.4401 4.6107 14.6375 4.37376L15.865 2.90082C16.0923 2.62802 16.0348 2.21757 15.7313 2.03336C13.6023 0.741604 11.0443 -0.00878906 8.28967 -0.00878906C5.53498 -0.00878906 2.97703 0.741604 0.848076 2.03336Z" fill="url(#paint0_linear_227_17691)"/>
            <defs>
            <linearGradient id="paint0_linear_227_17691" x1="-14.2856" y1="31.9985" x2="-14.2856" y2="43.9985" gradientUnits="userSpaceOnUse">
            <stop stop-color="#$color1"/>
            <stop offset="1" stop-color="#$color2"/>
            </linearGradient>
            </defs>
            </svg>
            """.trimIndent()
}

fun wifiSvg3(col1: String, col2: String): String {
    val color1 = processColor(col1)
    val color2 = processColor(col2)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="26" height="8" viewBox="0 0 26 8" fill="none">
<path d="M13 0.75C8.49174 0.75 4.32265 2.08025 0.952886 4.33048C0.779088 4.44654 0.746563 4.68639 0.878742 4.845L2.75001 7.09053C2.86516 7.2287 3.06547 7.25668 3.214 7.15733C5.93778 5.33539 9.32747 4.25 13 4.25C16.6725 4.25 20.0622 5.33539 22.786 7.15733C22.9345 7.25668 23.1348 7.2287 23.25 7.09053L25.1213 4.845C25.2534 4.68639 25.2209 4.44654 25.0471 4.33048C21.6774 2.08025 17.5083 0.75 13 0.75Z" fill="#$color1" stroke="#$color1" stroke-width="0.5"/>
</svg>""".trimIndent()
}