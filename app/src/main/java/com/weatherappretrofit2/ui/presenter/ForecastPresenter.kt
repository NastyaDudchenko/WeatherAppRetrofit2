package com.weatherappretrofit2.ui.presenter

import androidx.lifecycle.MutableLiveData
import com.weatherappretrofit2.model.api.ForecastListResponse
import com.weatherappretrofit2.model.api.WeatherResponse
import com.weatherappretrofit2.model.local.forecast.ForecastDataResult
import com.weatherappretrofit2.model.local.weather.DataResult
import com.weatherappretrofit2.network.WeatherRetrofitAdapter
import com.weatherappretrofit2.util.Constants
import com.weatherappretrofit2.util.Constants.DEFAULT_CITY_NAME
import com.weatherappretrofit2.util.apiToLocal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ForecastPresenter {

    private val weatherRetrofitAdapter = WeatherRetrofitAdapter()
    val forecastLiveData = MutableLiveData<ForecastDataResult>()
    val isLoading = MutableLiveData<Boolean>()

    fun getForecastByCityName(cityName: String?) {
        isLoading.postValue(true)
        weatherRetrofitAdapter.getForecast(cityName = cityName ?: DEFAULT_CITY_NAME)
    }

    val forecastResponse = object : Callback<ForecastListResponse> {
        override fun onResponse(
            call: Call<ForecastListResponse>,
            response: Response<ForecastListResponse>
        ) {
            isLoading.postValue(false)
            val forecastResponse = response.body()

            if (response.code() == Constants.API_SUCCESS) {
                val forecastData = apiToLocal(forecastResponse)
                forecastLiveData.postValue(
                    ForecastDataResult(
                        status = Constants.SUCCESS,
                        forecastData
                    )
                )
            } else {
                forecastLiveData.postValue(ForecastDataResult(status = Constants.ERROR, null))
            }
        }

        override fun onFailure(call: Call<ForecastListResponse>, t: Throwable) {
            isLoading.postValue(false)
            forecastLiveData.postValue(ForecastDataResult(status = Constants.ERROR, null))
        }
    }
}