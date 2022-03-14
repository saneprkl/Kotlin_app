package com.example.kotlin_app

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherViewModel: ViewModel() {
    val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://www.metaweather.com")
        .addConverterFactory( GsonConverterFactory.create() )
        .build()
        .create()
}