package ru.itis.karakurik.androidLab2.presentation.fragments.list.recycler;

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.itis.karakurik.androidLab2.domain.entity.Weather

class ListRecyclerAdapter(
    private val onItemClick: (id: Int) -> Unit
) : ListAdapter<Weather, ListItemViewHolder>(CityWeatherDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListItemViewHolder.create(
        parent,
        onItemClick
    )

    override fun onBindViewHolder(
        holder: ListItemViewHolder,
        position: Int
    ) = holder.bind(
        getItem(position)
    )
}
