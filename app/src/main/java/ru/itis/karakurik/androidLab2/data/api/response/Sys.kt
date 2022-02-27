package ru.itis.karakurik.androidLab2.data.api.response


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String
)
