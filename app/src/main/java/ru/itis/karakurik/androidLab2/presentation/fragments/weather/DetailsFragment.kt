package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import ru.itis.karakurik.androidLab2.WeatherApp
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.FragmentDetailsBinding
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.presentation.common.convertors.TempColorConverter
import ru.itis.karakurik.androidLab2.presentation.common.utils.AppViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: AppViewModelFactory

    private val viewModel: WeatherViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as WeatherApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        arguments?.getInt(getString(R.string.city_id))?.let {
            viewModel.onGetWeather(it)
        }
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                setWeatherData(it)
            }, onFailure = {
                Timber.e(it.message.toString())
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherData(weather: Weather) {
        with(binding) {
            weather.run {
                setWeather(weather)
                tvCity.text = name
                tvTemp.text = "${temp}°C"
                tvTempMin.text = "${tempMin}°C"
                tvTempMax.text = "${tempMax}°C"
                tvWindDeg.text = windDeg.toString()
                tvWindSpeed.text = "${windSpeed}km/h"
                tvHumidity.text = "${humidity}%"
                tvPressure.text = "${pressure}P"
                ivAir.load(iconUrl)

                arguments?.getString(R.string.transition_name.toString())?.let {
                    ivAir.transitionName = it
                }

                tvTemp.setTextColor(
                    ContextCompat.getColor(
                        tvTemp.context,
                        TempColorConverter.getColor(weather.temp)
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
