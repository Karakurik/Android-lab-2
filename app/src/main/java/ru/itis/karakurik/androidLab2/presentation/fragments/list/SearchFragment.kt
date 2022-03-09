package ru.itis.karakurik.androidLab2.presentation.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.FragmentSearchBinding
import ru.itis.karakurik.androidLab2.di.DiContainer
import ru.itis.karakurik.androidLab2.domain.entity.Weather
import ru.itis.karakurik.androidLab2.domain.enum.WindDeg
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeathersUseCase
import ru.itis.karakurik.androidLab2.presentation.fragments.list.recycler.ListRecyclerAdapter
import timber.log.Timber

private const val COUNT_OF_CITIES_IN_LIST = 20
private const val HARDCODE_LAT = 55.7887
private const val HARDCODE_LON = 49.1221

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var binding: FragmentSearchBinding? = null
    private var listRecyclerAdapter: ListRecyclerAdapter? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val getWeatherUseCase: GetWeatherUseCase by lazy {
        GetWeatherUseCase(DiContainer.weatherRepository, Dispatchers.Default)
    }

    private val getWeathersUseCase: GetWeathersUseCase by lazy {
        GetWeathersUseCase(DiContainer.weatherRepository, Dispatchers.Default)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initRecyclerView()

        binding?.svSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    Timber.d("Pressed query button")
                    try {
                        getWeather(query)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "Не удалось найти",
                            Toast.LENGTH_LONG
                        ).show()
                        Timber.e(ex.message.toString())
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.d("Query text changed")
                return false
            }
        })
    }

    private suspend fun getWeather(city: String) {
        val weather = getWeatherUseCase(city)
        showDetailsFragment(weather.id)
    }


    private fun initRecyclerView() {
        binding?.rvSearch?.run {
            listRecyclerAdapter = ListRecyclerAdapter { id ->
                showDetailsFragment(id)
            }
            adapter = listRecyclerAdapter
        }

        getWeathers(HARDCODE_LAT, HARDCODE_LON, COUNT_OF_CITIES_IN_LIST)
    }

    private fun getWeathers(lat: Double, lon: Double, cnt: Int) {
        lifecycleScope.launch {
            try {
                val weathers = getWeathersUseCase(lat, lon, cnt)
                listRecyclerAdapter?.submitList(weathers)
            } catch (ex: Exception) {
                Toast.makeText(
                    context,
                    "Не удалось найти",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showDetailsFragment(id: Int) {
        val bundle = bundleOf(
            "ID" to id
        )
        findNavController().navigate(R.id.action_fragment_search_to_fragment_details, bundle)
    }
}
