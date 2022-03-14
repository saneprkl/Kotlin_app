package com.example.kotlin_app

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherViewModel: ViewModel() {
    var error = false
    val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://www.metaweather.com")
        .addConverterFactory( GsonConverterFactory.create() )
        .build()
        .create()

    fun getCityData() {
        viewModelScope.launch {
            val response = api.findCity().awaitResponse()

            if(response.isSuccessful){
                val data: FindCityItem? = response.body()
                //Log.d("***", data?.woeid.toString() )
            } else {
                error = true
                Log.d("***", "not successful")
            }
        }
    }
}