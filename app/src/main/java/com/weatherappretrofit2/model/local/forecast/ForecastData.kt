package com.weatherappretrofit2.model.local.forecast

import java.io.Serializable

data class ForecastData(
    var count: Int? = 0,
    var forecastList: List<ForecastListItem>? = null
) : Serializable