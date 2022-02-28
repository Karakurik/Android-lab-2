package ru.itis.karakurik.androidLab2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.data.WeatherRepository
import ru.itis.karakurik.androidLab2.databinding.FragmentDetailsBinding
import ru.itis.karakurik.androidLab2.models.CityWeather

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var cityName: String? = null
    private var weather: CityWeather? = null

    private val repository by lazy {
        WeatherRepository()
    }

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        arguments?.let {
            cityName = it.getString("CITY_NAME")
        }

        lifecycleScope.launch {
            weather = cityName?.let { repository.getWeather(it) }
        }

        binding?.run {
            tvCity.text = weather?.name
            tvTemp.text = weather?.temp.toString() + "°C"
            tvHumidityValue.text = weather?.humidity.toString() + "%"
            tvWindDegValue.text = weather?.windDeg.toString()
        }
    }
}
