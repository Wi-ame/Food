package com.cscorner.food.videoModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cscorner.food.db.MealDataBase

class HomeViewModelFactory(
     private val mealDataBase: MealDataBase): ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass:Class<T>):T{
            return  HomeViewModel(mealDataBase) as T
        }
}