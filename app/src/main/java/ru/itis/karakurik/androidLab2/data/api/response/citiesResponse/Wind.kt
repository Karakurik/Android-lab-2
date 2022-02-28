package ru.itis.karakurik.androidLab2.data.api.response.citiesResponse


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    val speed: Double
)
