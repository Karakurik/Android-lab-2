package ru.itis.karakurik.androidLab2.ui.fragments.list.recycler;

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.ListItemCityBinding
import ru.itis.karakurik.androidLab2.models.CityWeather
import ru.itis.karakurik.androidLab2.models.convertors.TempColorConverter
import ru.itis.karakurik.androidLab2.models.convertors.TempColorConverter.getColor

class ListItemViewHolder(
    itemView: View,
    private val onItemClick: (city: String) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ListItemCityBinding.bind(itemView)

    fun bind(cityWeather: CityWeather) {
        with(binding) {
            tvCityItem.text = cityWeather.name
            tvTempItem.text = cityWeather.temp.toString()
            tvTempItem.setTextColor(getColor(cityWeather.temp))

            root.setOnClickListener {
                onItemClick(cityWeather.name)
            }
        }
    }

}
