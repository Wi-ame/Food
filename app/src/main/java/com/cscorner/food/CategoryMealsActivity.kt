package com.cscorner.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.cscorner.food.adapters.CategoryMealsAdapter
import com.cscorner.food.databinding.ActivityCategoryMealsBinding
import com.cscorner.food.pojo.MealList
import com.cscorner.food.videoModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecycleView()

        categoryMealsViewModel= ViewModelProviders.of(this,)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel .observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCatergoryCount.text=mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)

        })

    }

    private fun prepareRecycleView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply{
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}