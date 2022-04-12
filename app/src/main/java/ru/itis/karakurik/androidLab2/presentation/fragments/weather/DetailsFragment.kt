package ru.itis.karakurik.androidLab2.presentation.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.FragmentDetailsBinding
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.presentation.common.convertors.TempColorConverter
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

//    @Inject
//    lateinit var factory: WeatherViewModel.Factory

    private val viewModel: WeatherViewModel by viewModels()

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

//        arguments?.getInt(getString(R.string.city_id))?.let {
//            viewModel.onGetWeather(it)
//        }

        viewModel.onGetWeather(args.cityId)
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
                tvTemp.text = getString(R.string.temp_with_symbol, temp)
                tvTempMin.text = getString(R.string.temp_with_symbol, tempMin)
                tvTempMax.text = getString(R.string.temp_with_symbol, tempMax)
                tvWindDeg.text = windDeg.toString()
                tvWindSpeed.text = getString(R.string.wind_speed_with_symbol, windSpeed)
                tvHumidity.text = getString(R.string.humidity_with_symbol, humidity)
                tvPressure.text = getString(R.string.pressure_with_symbol, pressure)
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
