package ru.itis.karakurik.androidLab2.presentation.fragments.list

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.FragmentSearchBinding
import ru.itis.karakurik.androidLab2.di.DiContainer
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeatherUseCase
import ru.itis.karakurik.androidLab2.domain.usecase.GetWeathersUseCase
import ru.itis.karakurik.androidLab2.presentation.fragments.list.recycler.ListRecyclerAdapter
import timber.log.Timber

private const val COUNT_OF_CITIES_IN_LIST = 20
private const val DEFAULT_LAT = 55.7887
private const val DEFAULT_LON = 49.1221
private const val REQUEST_CODE_100 = 100

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var binding: FragmentSearchBinding? = null
    private var listRecyclerAdapter: ListRecyclerAdapter? = null

    private var userLat: Double = DEFAULT_LAT
    private var userLon: Double = DEFAULT_LON
    private var userLocation: FusedLocationProviderClient? = null

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

        getUserLocation()
        initRecyclerView()

        handleSearchQuery()

        initSwipeRefreshList()
    }

    private fun initSwipeRefreshList() {
        binding?.swipeRefreshLayout?.let {
            it.setOnRefreshListener {
                getUserLocation()
                it.isRefreshing = false
            }
        }
    }

    private fun getUserLocation() {
        context?.let {
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                Timber.d("Request location permissions")
                requestPermissions(permissions, REQUEST_CODE_100)
            } else {
                Timber.d("Get user location")
                userLocation = LocationServices.getFusedLocationProviderClient(requireContext())
                userLocation?.lastLocation?.addOnSuccessListener { location ->
                    if (location != null) {
                        userLon = location.longitude
                        userLat = location.latitude
                        Timber.d("Location found")
                        /*binding?.let { it ->
                            Snackbar.make(it.root, "Местоположение найдено", Snackbar.LENGTH_LONG)
                                .show()
                        }*/
                        getWeathers(userLat, userLon, COUNT_OF_CITIES_IN_LIST)
                    } else {
                        Timber.d("Location not found")
                        binding?.let { it ->
                            Snackbar.make(it.root, "Местоположение не найдено\nВключите геолокацию", Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_100 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    Timber.d("Permissions granted")
                    getUserLocation()
                } else {
                    Timber.d("Permissions denied")
                    binding?.let {
                        Snackbar.make(it.root, "Разрешения не даны", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


    private fun handleSearchQuery() {
        binding?.svSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    Timber.d("Pressed query button")
                    try {
                        getWeather(query)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "Не удалось найти акой город",
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

        getWeathers(userLat, userLon, COUNT_OF_CITIES_IN_LIST)
    }

    private fun getWeathers(lat: Double, lon: Double, cnt: Int) {
        Timber.d("Get weathers list")
        lifecycleScope.launch {
            try {
                val weathers = getWeathersUseCase(lat, lon, cnt)
                listRecyclerAdapter?.submitList(weathers)
                binding?.rvSearch?.layoutManager?.scrollToPosition(0)
            } catch (ex: Exception) {
                Timber.e("Error due to get weathers")
                Timber.e(ex.message.toString())
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
