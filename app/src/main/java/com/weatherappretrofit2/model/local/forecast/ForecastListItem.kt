package com.weatherappretrofit2.model.local.forecast

data class ForecastListItem(
    val status: String,
    val imageResource: String,
    val text1: String,
    val text2: String,
    val text3: String
)