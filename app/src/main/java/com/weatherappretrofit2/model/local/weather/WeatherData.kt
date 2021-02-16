package com.weatherappretrofit2.model.local.weather

import java.io.Serializable

data class WeatherData(
    val name: String,
    val address: String,
    val updatedAt: Float,
    val temp: Int,
    val tempMax: Int,
    val tempMin: Int,
    val pressure: Int,
    val humidity: Int,
    val sunrise: Float,
    val sunset: Float,
    val windSpeed: Int,
    val weatherDescription: String,
    val icon: String
) : Serializable