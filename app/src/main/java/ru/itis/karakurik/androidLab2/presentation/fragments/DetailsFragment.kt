package ru.itis.karakurik.androidLab2.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.FragmentDetailsBinding
import ru.itis.karakurik.androidLab2.di.DiContainer
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var cityId: Int? = null
    private var weather: Weather? = null

    private val getWeatherUseCase: GetWeatherUseCase by lazy {
        GetWeatherUseCase(DiContainer.weatherRepository, Dispatchers.Default)
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
            cityId = it.getInt("ID")
        }

        lifecycleScope.launch {
            weather = cityId?.let { getWeatherUseCase(it) }

            binding?.run {
                weather?.let {
                    tvCity.text = it.name
                    tvTemp.text = it.temp.toString() + "°C"
                    tvHumidityValue.text = it.humidity.toString() + "%"
                    tvWindDegValue.text = it.windDeg.toString()
                    ivAir.context.imageLoader.execute(
                        ImageRequest.Builder(ivAir.context)
                            .data(it.iconUrl)
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .placeholder(R.drawable.air)
                            .target(ivAir)
                            .build()
                    )
                }
            }
        }
    }
}
