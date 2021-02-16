package com.weatherappretrofit2.network

import com.weatherappretrofit2.model.api.ForecastListResponse
import com.weatherappretrofit2.model.api.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    //TODO use or remove this
    @GET("data/2.5/weather?units=metric")
    fun getCurrentWeatherDataByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("APPID") app_id: String
    ): Call<WeatherResponse>

    @GET("/data/2.5/weather?units=metric")
    fun getCurrentWeatherDataByCityName(
        @Query("q") cityName: String,
        @Query("APPID") app_id: String
    ): Call<WeatherResponse>

    @GET("/data/2.5/forecast?units=metric" )
    fun getForecastListDataByCityName(
        @Query("q") cityName: String,
        @Query("appid") app_id: String
    ): Call<ForecastListResponse>
}
