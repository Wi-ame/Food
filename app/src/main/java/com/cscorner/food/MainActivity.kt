package com.cscorner.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ButtonNavigation =findViewById<BottomNavigationView>(R.id.btn_nav)
        val NavController= Navigation.findNavController( this ,R.id.host_fragment)
        NavigationUI.setupWithNavController(ButtonNavigation,NavController)
    }
}