package com.example.basekotlin.util.customview.battery


fun svgPin0(color1: String): String {
    val cl1 = processColor(color1)
    return """<svg xmlns="http://www.w3.org/2000/svg" width="67" height="36" viewBox="0 0 67 36" fill="none">
        <path opacity="0.2" d="M0.54541 5.95041C0.54541 2.66409 3.2095 0 6.49582 0H54.595C57.8813 0 60.5454 2.66409 60.5454 5.95041V7.8C60.5454 8.46274 61.0827 9 61.7454 9V9C64.3964 9 66.5454 11.149 66.5454 13.8V22.2C66.5454 24.851 64.3964 27 61.7454 27V27C61.0827 27 60.5454 27.5373 60.5454 28.2V30.0496C60.5454 33.3359 57.8813 36 54.595 36H6.49582C3.2095 36 0.54541 33.3359 0.54541 30.0496V5.95041Z" fill="#$cl1"/>
        <rect x="3.54541" y="3" width="45" height="30" rx="6" fill="#$cl1"/>
        </svg>""".trimIndent()
}


fun svgPin1(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """
            <svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
                <path d="M78 12H80C84.4183 12 88 15.5817 88 20V28C88 32.4183 84.4183 36 80 36H78V12Z" fill="#${
        color1.trim().replace("#", "").replace(" ", "")
    }"/>
                <path d="M79 12V13H80H84C85.6569 13 87 14.3431 87 16V32C87 33.6569 85.6569 35 84 35H80H79V36V40C79 43.866 75.866 47 72 47H8C4.13401 47 1 43.866 1 40V8C1 4.13401 4.13401 1 8 1H72C75.866 1 79 4.13401 79 8V12Z" stroke="#${
        color1.trim().replace("#", "").replace(" ", "")
    }" stroke-width="2"/>
                <path d="M5 11C5 7.68629 7.68629 5 11 5H69C72.3137 5 75 7.68629 75 11V37C75 40.3137 72.3137 43 69 43H11C7.68629 43 5 40.3137 5 37V11Z" fill="#${
        color2.trim().replace("#", "").replace(" ", "")
    }"/>
            </svg>
        """.trimIndent()
}

fun svgPin2(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path d="M78 12H80C84.4183 12 88 15.5817 88 20V28C88 32.4183 84.4183 36 80 36H78V12Z" fill="#${
        color1.trim().replace("#", "").replace(" ", "")
    }"/>
<path d="M79 12V13H80H84C85.6569 13 87 14.3431 87 16V32C87 33.6569 85.6569 35 84 35H80H79V36V40C79 43.866 75.866 47 72 47H8C4.13401 47 1 43.866 1 40V8C1 4.13401 4.13401 1 8 1H72C75.866 1 79 4.13401 79 8V12Z" stroke="#${
        color1.trim().replace("#", "").replace(" ", "")
    }" stroke-width="2"/>
<path d="M4 12C4 7.58172 7.58172 4 12 4H58.7352C61.9556 4 64.6017 6.54235 64.7304 9.76019L64.837 12.4254C64.9394 14.9851 66.2615 17.341 68.393 18.762L69.1519 19.268C72.6203 21.5802 73.4777 26.3087 71.0421 29.6915L68.5473 33.1565C67.4352 34.7011 67.8481 36.8628 69.4512 37.8888C72.2427 39.6753 70.9773 44 67.663 44H12C7.58172 44 4 40.4183 4 36V12Z" fill="#${
        color2.trim().replace("#", "").replace(" ", "")
    }"/>
</svg>""".trimIndent()
}


fun svgPin3(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path d="M78 12H80C84.4183 12 88 15.5817 88 20V28C88 32.4183 84.4183 36 80 36H78V12Z" fill="#${
        color1.trim().replace("#", "").replace(" ", "")
    }"/>
<path d="M79 12V13H80H84C85.6569 13 87 14.3431 87 16V32C87 33.6569 85.6569 35 84 35H80H79V36V40C79 43.866 75.866 47 72 47H8C4.13401 47 1 43.866 1 40V8C1 4.13401 4.13401 1 8 1H72C75.866 1 79 4.13401 79 8V12Z" stroke="#${
        color1.trim().replace("#", "").replace(" ", "")
    }" stroke-width="2"/>
<path d="M4 12C4 7.58172 7.58172 4 12 4L35.9172 4C38.6229 4 40.9939 5.81093 41.7058 8.4213L42.662 11.9273C43.2001 13.9005 44.4735 15.5926 46.2205 16.656L48.6788 18.1523C52.2025 20.2972 53.3885 24.8511 51.3587 28.4423L50.2496 30.4046C49.2156 32.234 49.5283 34.5283 51.0142 36.0142C53.9612 38.9612 51.874 44 47.7064 44H12C7.58172 44 4 40.4183 4 36V12Z" fill="#${
        color2.trim().replace("#", "").replace(" ", "")
    }"/>
</svg>""".trimIndent()
}


fun svgPin4(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path d="M78 12H80C84.4183 12 88 15.5817 88 20V28C88 32.4183 84.4183 36 80 36H78V12Z" fill="#${
        color1.trim().replace("#", "").replace(" ", "")
    }"/>
<path d="M79 12V13H80H84C85.6569 13 87 14.3431 87 16V32C87 33.6569 85.6569 35 84 35H80H79V36V40C79 43.866 75.866 47 72 47H8C4.13401 47 1 43.866 1 40V8C1 4.13401 4.13401 1 8 1H72C75.866 1 79 4.13401 79 8V12Z" stroke="#${
        color1.trim().replace("#", "").replace(" ", "")
    }" stroke-width="2"/>
<path d="M4 12C4 7.58172 7.58172 4 12 4L35.9172 4C38.6229 4 40.9939 5.81093 41.7058 8.4213L42.662 11.9273C43.2001 13.9005 44.4735 15.5926 46.2205 16.656L48.6788 18.1523C52.2025 20.2972 53.3885 24.8511 51.3587 28.4423L50.2496 30.4046C49.2156 32.234 49.5283 34.5283 51.0142 36.0142C53.9612 38.9612 51.874 44 47.7064 44H12C7.58172 44 4 40.4183 4 36V12Z" fill="#${
        color2.trim().replace("#", "").replace(" ", "")
    }"/>
</svg>""".trimIndent()
}


fun svgPin5(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path d="M78 12H80C84.4183 12 88 15.5817 88 20V28C88 32.4183 84.4183 36 80 36H78V12Z" fill="#${
        color1.trim().replace("#", "").replace(" ", "")
    }"/>
<path d="M79 12V13H80H84C85.6569 13 87 14.3431 87 16V32C87 33.6569 85.6569 35 84 35H80H79V36V40C79 43.866 75.866 47 72 47H8C4.13401 47 1 43.866 1 40V8C1 4.13401 4.13401 1 8 1H72C75.866 1 79 4.13401 79 8V12Z" stroke="#${
        color1.trim().replace("#", "").replace(" ", "")
    }" stroke-width="2"/>
<path d="M4 12C4 7.58172 7.58172 4 12 4H15.0843C18.9375 4 21.624 7.82236 20.3188 11.4478C19.2335 14.4625 20.2065 17.8332 22.7314 19.8058L31.238 26.4516C33.0618 27.8764 33.4392 30.4851 32.094 32.3684L31.4969 33.2044C30.4046 34.7336 30.7949 36.8633 32.3585 37.9057C35.097 39.7314 33.8046 44 30.5133 44H12C7.58172 44 4 40.4183 4 36V12Z" fill="#${
        color2.trim().replace("#", "").replace(" ", "")
    }"/>
</svg>""".trimIndent()
}


fun svgPin6(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path fill-rule="evenodd" clip-rule="evenodd" d="M0 2C0 0.895431 0.89543 0 2 0H80C81.1046 0 82 0.895431 82 2V14C82 15.1046 82.8954 16 84 16H86C87.1046 16 88 16.8954 88 18V30C88 31.1046 87.1046 32 86 32H84C82.8954 32 82 32.8954 82 34V46C82 47.1046 81.1046 48 80 48H2C0.895429 48 0 47.1046 0 46V2ZM24.4211 42H22.3796H8C6.89543 42 6 41.1046 6 40V8C6 6.89543 6.89543 6 8 6H17.0526H19.0941H35.4737H37.5151H53.8947H55.9362H74C75.1046 6 76 6.89543 76 8V40C76 41.1046 75.1046 42 74 42H61.2632H59.2217H42.8421H40.8006H24.4211ZM43.7376 36.401C43.9281 37.3317 44.747 38 45.697 38H55.9522C57.2179 38 58.1654 36.839 57.9115 35.599L52.9993 11.599C52.8088 10.6683 51.9899 10 51.0399 10H40.7847C39.5189 10 38.5715 11.161 38.8253 12.401L43.7376 36.401ZM32.6188 10C33.5688 10 34.3877 10.6683 34.5782 11.599L39.4905 35.599C39.7443 36.839 38.7969 38 37.5311 38H27.2759C26.3259 38 25.507 37.3317 25.3165 36.401L20.4042 12.401C20.1504 11.161 21.0979 10 22.3636 10H32.6188ZM16.1572 11.599C15.9667 10.6683 15.1478 10 14.1978 10H12C10.8954 10 10 10.8954 10 12V36C10 37.1046 10.8954 38 12 38H19.1101C20.3758 38 21.3232 36.839 21.0694 35.599L16.1572 11.599Z" fill="url(#paint0_linear_148_19997)"/>
<defs>
<linearGradient id="paint0_linear_148_19997" x1="89.5714" y1="16" x2="89.5714" y2="32" gradientUnits="userSpaceOnUse">
<stop stop-color="#${color1.trim().replace("#", "").replace(" ", "")}"/>
<stop offset="1" stop-color="#${color2.trim().replace("#", "").replace(" ", "")}"/>
</linearGradient>
</defs>
</svg>""".trimIndent()
}


fun svgPin7(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="88" height="48" viewBox="0 0 88 48" fill="none">
<path d="M88 18C88 16.8954 87.1046 16 86 16H84C82.8954 16 82 16.8954 82 18V30C82 31.1046 82.8954 32 84 32H86C87.1046 32 88 31.1046 88 30V18Z" fill="url(#paint0_linear_148_20004)"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M4 0C1.79086 0 0 1.79086 0 4V44C0 46.2091 1.79086 48 4 48H76C78.2091 48 80 46.2091 80 44V4C80 1.79086 78.2091 0 76 0H4ZM26.9474 40C25.8428 40 24.9474 39.1046 24.9474 38V10C24.9474 8.89543 25.8428 8 26.9474 8H37.8947C38.9993 8 39.8947 8.89543 39.8947 10V38C39.8947 39.1046 38.9993 40 37.8947 40H26.9474ZM18.9474 40C20.0519 40 20.9474 39.1046 20.9474 38V10C20.9474 8.89543 20.0519 8 18.9474 8H10C8.89543 8 8 8.89543 8 10V38C8 39.1046 8.89543 40 10 40H18.9474ZM6 4H20.9474H22.9474H24.9474H39.8947H41.8947H43.8947H58.8421H60.8421H62.8421H74C75.1046 4 76 4.89543 76 6V42C76 43.1046 75.1046 44 74 44H62.8421H60.8421H58.8421H43.8947H41.8947H39.8947H24.9474H22.9474H20.9474H6C4.89543 44 4 43.1046 4 42V6C4 4.89543 4.89543 4 6 4Z" fill="url(#paint1_linear_148_20004)"/>
<defs>
<linearGradient id="paint0_linear_148_20004" x1="81.8929" y1="21.3333" x2="81.8929" y2="26.6667" gradientUnits="userSpaceOnUse">
<stop stop-color="#${color1.trim().replace("#", "").replace(" ", "")}"/>
<stop offset="1" stop-color="#${color2.trim().replace("#", "").replace(" ", "")}"/>
</linearGradient>
<linearGradient id="paint1_linear_148_20004" x1="81.4286" y1="16" x2="81.4286" y2="32" gradientUnits="userSpaceOnUse">
<stop stop-color="#${color1.trim().replace("#", "").replace(" ", "")}"/>
<stop offset="1" stop-color="#${color2.trim().replace("#", "").replace(" ", "")}"/>
</linearGradient>
</defs>
</svg>""".trimIndent()
}


fun svgPin8(color1: String = "#FF6C6F", color2: String = "#48ADFF"): String {
    return """<svg xmlns="http://www.w3.org/2000/svg" width="92" height="52" viewBox="0 0 92 52" fill="none">
<path d="M78 50C84.6274 50 90 39.2548 90 26C90 12.7452 84.6274 2 78 2M78 50C71.3726 50 66 39.2548 66 26C66 12.7452 71.3726 2 78 2M78 50H16M78 2H16C8.49976 2 2 12.7452 2 26C2 39.2548 8.49976 49.9888 16 49.9888M84 34C86.2091 34 88 30.4183 88 26C88 21.5817 86.2091 18 84 18M84 34C81.7909 34 80 30.4183 80 26C80 21.5817 81.7909 18 84 18M84 34H78C75.7909 34 74 30.4183 74 26C74 21.5817 75.7909 18 78 18H84" stroke="url(#paint0_linear_148_20012)" stroke-width="3"/>
<path d="M8 25C8 13.8913 13.2468 9.07866 15.9343 8.02521C15.9789 8.0077 16.0246 8 16.0725 8H47.2526C47.5721 8 47.7691 8.35885 47.6125 8.63734C46.2104 11.1315 44 16.9882 44 25C44 33.2254 46.3298 40.3441 47.7229 43.4164C47.8457 43.6872 47.6501 44 47.3528 44H16.1147C16.0398 44 15.9688 43.9809 15.9059 43.94C13.21 42.19 8 36.0697 8 25Z" fill="url(#paint1_linear_148_20012)"/>
<defs>
<linearGradient id="paint0_linear_148_20012" x1="46" y1="2" x2="46" y2="50" gradientUnits="userSpaceOnUse">
<stop stop-color="#${color1.trim().replace("#", "").replace(" ", "")}"/>
<stop offset="1" stop-color="#${color2.trim().replace("#", "").replace(" ", "")}"/>
</linearGradient>
<linearGradient id="paint1_linear_148_20012" x1="48.7143" y1="20" x2="48.7143" y2="32" gradientUnits="userSpaceOnUse">
<stop stop-color="#${color1.trim().replace("#", "").replace(" ", "")}"/>
<stop offset="1" stop-color="#${color2.trim().replace("#", "").replace(" ", "")}"/>
</linearGradient>
</defs>
</svg>""".trimIndent()
}

