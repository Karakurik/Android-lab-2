package ru.itis.karakurik.androidLab2.presentation.fragments.cities

import android.Manifest.permission.*
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.karakurik.androidLab2.databinding.FragmentSearchBinding
import ru.itis.karakurik.androidLab2.presentation.fragments.cities.recycler.ListRecyclerAdapter
import timber.log.Timber
import javax.inject.Inject

private const val COUNT_OF_CITIES_IN_LIST = 20
private const val DEFAULT_LAT = 55.7887
private const val DEFAULT_LON = 49.1221

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        private val permissions = arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION,
            ACCESS_LOCATION_EXTRA_COMMANDS
        )
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var listRecyclerAdapter: ListRecyclerAdapter? = null
    private var userLat: Double = DEFAULT_LAT
    private var userLon: Double = DEFAULT_LON

    private val viewModel: CityListViewModel by viewModels()

    @Inject
    lateinit var smoothScroller: RecyclerView.SmoothScroller

    @Inject
    lateinit var userLocation: FusedLocationProviderClient

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var allPermissionsGranted = true
            for (granted in it.values) {
                allPermissionsGranted = allPermissionsGranted and granted
            }
            if (allPermissionsGranted) {
                getUserLocation()
            } else {
                Toast.makeText(
                    context,
                    "Разрешения не даны",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        getUserLocation()
        initRecyclerView()
        handleSearchQuery()
        initSwipeRefreshList()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.weatherList.collect { result ->
                result.fold(
                    onSuccess = {
                        listRecyclerAdapter?.submitList(it)
                        scrollRecyclerViewToPosition(0)
                        binding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        Timber.e("Error due to get weathers")
                        Timber.e(it.message.toString())
                        Toast.makeText(
                            context,
                            "Не удалось найти",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cityId.collect { result ->
                result?.fold(
                    onSuccess = {
                        showDetailsFragment(it)
                    },
                    onFailure = {
                        Toast.makeText(
                            context,
                            "Не удалось найти такой город",
                            Toast.LENGTH_LONG
                        ).show()
                        Timber.d("Do not found city")
                    }
                )
            }
        }
    }

    private fun initSwipeRefreshList() {
        binding.swipeRefreshLayout.let {
            it.setOnRefreshListener {
                getUserLocation()
                updateWeatherList(userLat, userLon, COUNT_OF_CITIES_IN_LIST)
            }
        }
    }

    private fun getUserLocation() {
        context?.let { context ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    ACCESS_COARSE_LOCATION
                ) == PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    ACCESS_FINE_LOCATION
                ) == PERMISSION_DENIED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    ACCESS_LOCATION_EXTRA_COMMANDS
                ) == PERMISSION_DENIED
            ) {
                Timber.d("Request location permissions")
                requestPermissions.launch(permissions)
            } else {
                Timber.d("Get user location")
                userLocation.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        userLon = location.longitude
                        userLat = location.latitude
                        Timber.d("Location found")
                        Toast.makeText(
                            context,
                            "Местоположение найдено",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Timber.d("Location not found")
                        binding.let { it ->
                            Snackbar.make(
                                it.root,
                                "Местоположение не найдено\nВключите геолокацию",
                                Snackbar.LENGTH_LONG
                            ).run {
                                setAction("Включить местоположение") {
                                    startActivity(
                                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    )
                                }
                                show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleSearchQuery() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.d("Pressed query button")
                viewModel.onGetCityId(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Timber.d("Query text changed")
                return false
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvSearch.run {
            listRecyclerAdapter = ListRecyclerAdapter { id ->
                showDetailsFragment(id)
            }
            adapter = listRecyclerAdapter
        }
        updateWeatherList(userLat, userLon, COUNT_OF_CITIES_IN_LIST)
    }

    private fun updateWeatherList(lat: Double, lon: Double, cnt: Int) {
        viewModel.onGetWeatherList(lat, lon, cnt)
        Timber.d("Get weathers list")
    }

    private fun showDetailsFragment(
        id: Int,
    ) = findNavController().navigate(
        SearchFragmentDirections.actionFragmentSearchToFragmentDetails(
            id,
            "Item_$id"
        )
    )

    private fun scrollRecyclerViewToPosition(position: Int) {
        binding.rvSearch.layoutManager?.startSmoothScroll(smoothScroller.apply {
            targetPosition = position
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        listRecyclerAdapter = null
        _binding = null
    }
}
