package ru.itis.karakurik.androidLab2.data.api.response.citiesResponse


import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<City>,
    @SerializedName("message")
    val message: String
)
