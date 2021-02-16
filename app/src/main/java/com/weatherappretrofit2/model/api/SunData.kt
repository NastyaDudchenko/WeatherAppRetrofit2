package com.weatherappretrofit2.model.api

import com.google.gson.annotations.SerializedName

class SunData {

    @SerializedName("country")
    var country: String? = null

    @SerializedName("sunrise")
    var sunrise: Float = 0.toFloat()

    @SerializedName("sunset")
    var sunset: Float = 0.toFloat()
}