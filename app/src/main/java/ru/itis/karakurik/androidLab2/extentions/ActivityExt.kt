package ru.itis.karakurik.androidLab2.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.findController (id: Int) : NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}
