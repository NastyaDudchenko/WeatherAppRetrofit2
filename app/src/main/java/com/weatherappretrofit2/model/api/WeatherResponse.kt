package com.weatherappretrofit2.model.api

import com.google.gson.annotations.SerializedName

class WeatherResponse(val icon: Any?, val temp: String?) {
    @SerializedName("coord")
    var coordinates: Coordinates? = null

    @SerializedName("sys")
    var sys: SunData? = null

    @SerializedName("weather")
    var weather = ArrayList<Weather>()

    @SerializedName("main")
    var mainWeatherData: MainWeatherData? = null

    @SerializedName("wind")
    var wind: Wind? = null

    @SerializedName("rain")
    var rain: Rain? = null

    @SerializedName("clouds")
    var clouds: Clouds? = null

    @SerializedName("dt")
    var dt: Float = 0.toFloat()

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("cod")
    var cod: Float = 0.toFloat()
}