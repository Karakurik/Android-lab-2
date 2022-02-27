package ru.itis.karakurik.androidLab2.ui.fragments.list.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.itis.karakurik.androidLab2.models.CityWeather

object CityWeatherDiffCallback : DiffUtil.ItemCallback<CityWeather>(){
    override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
        TODO("Not yet implemented")
    }

}
