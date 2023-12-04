package com.cscorner.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.cscorner.food.db.MealDataBase
import com.cscorner.food.videoModel.HomeViewModel
import com.cscorner.food.videoModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     val viewModel: HomeViewModel by lazy{
        val mealDataBase = MealDataBase.getInstance(this)
        val homeViewModelProviderFactory= HomeViewModelFactory(mealDataBase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ButtonNavigation =findViewById<BottomNavigationView>(R.id.btn_nav)
        val NavController= Navigation.findNavController( this ,R.id.host_fragment)
        NavigationUI.setupWithNavController(ButtonNavigation,NavController)
    }
}