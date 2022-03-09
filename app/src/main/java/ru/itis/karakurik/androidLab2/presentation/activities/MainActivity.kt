package ru.itis.karakurik.androidLab2.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import ru.itis.karakurik.androidLab2.databinding.ActivityMainBinding
import ru.itis.karakurik.androidLab2.extentions.findController

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        controller = binding?.navHostFragmentMain?.id?.let { findController(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        controller = null
    }
}
