package com.weatherappretrofit2.network

import com.weatherappretrofit2.ui.presenter.ForecastPresenter.forecastResponse
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.weatherResponse
import com.weatherappretrofit2.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRetrofitAdapter {

    private fun initRetrofit(): WeatherService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherService::class.java)
    }

    fun getCurrentDataByCityName(cityName: String) {
        val call = initRetrofit().getCurrentWeatherDataByCityName(
            cityName,
            Constants.APP_ID
        )

        call.enqueue(weatherResponse)
    }

    fun getForecast(cityName: String) {
        val call = initRetrofit().getForecastListDataByCityName(
            cityName = cityName,
            Constants.APP_ID
        )

        call.enqueue(forecastResponse)
    }
}