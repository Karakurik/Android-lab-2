package ru.itis.karakurik.androidLab2.data.api.response


import com.google.gson.annotations.SerializedName

data class WeatherCitiesResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<WeatherResponse>,
    @SerializedName("message")
    val message: String
)
