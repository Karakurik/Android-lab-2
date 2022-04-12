package ru.itis.karakurik.androidLab2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.karakurik.androidLab2.R
import ru.itis.karakurik.androidLab2.databinding.ActivityMainBinding
import ru.itis.karakurik.androidLab2.extentions.findController

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private var controller: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = binding.navHostFragmentMain.id.let { findController(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }
}
