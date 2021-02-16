package com.weatherappretrofit2.util

import com.weatherappretrofit2.model.api.ForecastListResponse
import com.weatherappretrofit2.model.api.WeatherResponse
import com.weatherappretrofit2.model.local.forecast.ForecastData
import com.weatherappretrofit2.model.local.forecast.ForecastListItem
import com.weatherappretrofit2.model.local.weather.WeatherData
import java.text.SimpleDateFormat
import java.util.*

fun apiToLocal(weatherResponse: WeatherResponse?): WeatherData? {
    var localModel: WeatherData? = null

    weatherResponse?.apply {
        localModel = WeatherData(
            name = name ?: "",
            address = sys?.country ?: "",
            temp = mainWeatherData?.temp?.toInt() ?: 0,
            tempMax = mainWeatherData?.temp_max?.toInt() ?: 0,
            tempMin = mainWeatherData?.temp_min?.toInt() ?: 0,
            pressure = mainWeatherData?.pressure?.toInt() ?: 0,
            humidity = mainWeatherData?.humidity?.toInt() ?: 0,
            sunrise = sys?.sunrise ?: 0f,
            sunset = sys?.sunset ?: 0f,
            windSpeed = wind?.speed?.toInt() ?: 0,
            weatherDescription = weather[0].description ?: "",
            icon = weather[0].icon ?: "",
            updatedAt = dt
        )
    }

    return localModel
}

fun apiToLocal(forecastResponse: ForecastListResponse?): ForecastData? {
    var localModel: ForecastData? = null

    forecastResponse?.apply {
        val weatherList = emptyList<ForecastListItem>().toMutableList()
        for (i in forecastResponse.weatherList ?: emptyList()) {
            val weatherItem = ForecastListItem(
                i.sys?.country ?: "",
                """http://openweathermap.org/img/wn/${i.weather[0].icon?:""}@2x.png""",
                i.mainWeatherData?.temp?.toInt().toString() + "℃",
                SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.ENGLISH
                ).format(i.dt * 1000),
                i.weather[0].description?.toUpperCase(Locale.getDefault()) ?: ""
            )
            weatherList.add(weatherItem)
        }

        localModel = ForecastData(
            count = count,
            forecastList = weatherList
        )
    }

    return localModel
}

