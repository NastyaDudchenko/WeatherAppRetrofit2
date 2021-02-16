package com.weatherappretrofit2.model.local.weather

import java.io.Serializable

data class DataResult(
    val status: String,
    val weatherData: WeatherData?
) : Serializable