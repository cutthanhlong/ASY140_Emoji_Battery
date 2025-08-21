package com.example.basekotlin.data.call_api

data class EmojiDataResponse(
    val icons: List<IconModel>,
    val batteries: List<BatteryModel>,
    val colorTemplates: List<ColorTemplateModel>,
    val batteryTemplates: List<BatteryTemplateModel>,
    val emojis: List<EmojiModel>,
    val animations: List<AnimationModel>
)