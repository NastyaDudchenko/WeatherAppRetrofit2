package com.weatherappretrofit2.ui.presenter

import androidx.lifecycle.MutableLiveData
import com.weatherappretrofit2.model.api.WeatherResponse
import com.weatherappretrofit2.model.local.weather.DataResult
import com.weatherappretrofit2.network.WeatherRetrofitAdapter
import com.weatherappretrofit2.util.Constants
import com.weatherappretrofit2.util.Constants.DEFAULT_CITY_NAME
import com.weatherappretrofit2.util.apiToLocal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeatherPresenter {

    private val weatherRetrofitAdapter = WeatherRetrofitAdapter()

    val weatherLiveData = MutableLiveData<DataResult>()
    val currentCity = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        currentCity.postValue("London")
    }

    fun getCurrentWeatherByCityName(cityName: String?) {
        isLoading.postValue(true)
        weatherRetrofitAdapter.getCurrentDataByCityName(cityName = cityName ?: DEFAULT_CITY_NAME)
    }

    val weatherResponse = object : Callback<WeatherResponse> {
        override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
            isLoading.postValue(false)
            val weatherResponse = response.body()

            when {
                response.code() == Constants.API_SUCCESS -> {
                    val weatherData = apiToLocal(weatherResponse)
                    weatherLiveData.postValue(DataResult(status = Constants.SUCCESS, weatherData))
                }
                response.code() == 404 -> {
                    weatherLiveData.postValue(DataResult(status = Constants.EMPTY, null))
                }
                else -> {
                    weatherLiveData.postValue(DataResult(status = Constants.ERROR, null))
                }
            }
        }

        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            isLoading.postValue(false)
            weatherLiveData.postValue(DataResult(status = Constants.ERROR, null))
        }
    }
}