package com.weatherappretrofit2.model.api

import com.google.gson.annotations.SerializedName

class ForecastListResponse {
    @SerializedName("cod")
    var status: Int = 0

    @SerializedName("message")
    var message: String? = null

    @SerializedName("cnt")
    var count: Int? = 0

    @SerializedName("list")
    var weatherList: List<WeatherResponse>? = null
}