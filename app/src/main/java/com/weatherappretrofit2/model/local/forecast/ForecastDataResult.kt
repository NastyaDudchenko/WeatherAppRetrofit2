package com.weatherappretrofit2.model.local.forecast

import java.io.Serializable

data class ForecastDataResult(
    val status: String,
    val forecastData: ForecastData?
) : Serializable