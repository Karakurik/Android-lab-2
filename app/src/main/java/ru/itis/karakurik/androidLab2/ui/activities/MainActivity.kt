package ru.itis.karakurik.androidLab2.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.itis.karakurik.androidLab2.data.WeatherRepository
import ru.itis.karakurik.androidLab2.databinding.ActivityMainBinding
import ru.itis.karakurik.androidLab2.extentions.findController

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null
    private val repository by lazy {
        WeatherRepository()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        controller = binding?.navHostFragmentMain?.id?.let { findController(it) }

        lifecycleScope.launch {
            try {
                val response = repository.getWeather("Kazan")

            } catch (ex: Exception) {
                Log.e("arg", ex.message.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        controller = null
    }
}
