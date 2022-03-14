package com.example.kotlin_app

import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {

    @GET("/api/location/search/?query=")

    fun findCity(): Call<FindCity>
}