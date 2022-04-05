package ru.itis.karakurik.androidLab2.presentation.fragments.cities.recycler;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.ListItemCityBinding
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.presentation.common.convertors.TempColorConverter.getColor

class ListItemViewHolder(
    private val binding: ListItemCityBinding,
    private val onItemClick: (id: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: Weather) {
        with(binding) {
            tvCityItem.text = weather.name
            tvTempItem.text = itemView.context.resources.getString(R.string.temp_with_symbol, weather.temp)
            tvTempItem.setTextColor(
                ContextCompat.getColor(
                    tvTempItem.context,
                    getColor(weather.temp)
                )
            )
            ivIconItem.run {
                load(weather.iconUrl)
                transitionName = "Item_${weather.id}"
            }

            root.setOnClickListener {
                onItemClick(weather.id)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (id: Int) -> Unit
        ) = ListItemViewHolder(
            ListItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }
}
