package ru.itis.karakurik.androidLab2.ui.fragments.list.recycler;

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.itis.karakurik.androidLab2.models.CityWeather

class ListRecyclerAdapter(

) : ListAdapter<CityWeather, ListViewHolder>(CityWeatherDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}
