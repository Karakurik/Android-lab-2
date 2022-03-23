package ru.itis.karakurik.androidLab2.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearSmoothScroller

import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.findController (id: Int) : NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}
