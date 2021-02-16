package com.weatherappretrofit2.model.api

import com.google.gson.annotations.SerializedName

class Coordinates {

    @SerializedName("lon")
    var lon: Float = 0.toFloat()

    @SerializedName("lat")
    var lat: Float = 0.toFloat()
}