package com.example.kotlin_app

data class FindCityItem(
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)