package ru.itis.karakurik.androidLab2.data.api.response.weatherResponse


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)
