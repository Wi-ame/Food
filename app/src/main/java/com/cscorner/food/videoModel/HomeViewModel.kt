package com.cscorner.food.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cscorner.food.pojo.CategoryList
import com.cscorner.food.pojo.CategoryMeals
import com.cscorner.food.pojo.Meal
import com.cscorner.food.pojo.MealList
import com.cscorner.food.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class HomeViewModel() : ViewModel(){
    private var RandomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData =MutableLiveData<List<CategoryMeals>>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    RandomMealLiveData.value =randomMeal
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }
    fun  getPopularItems(){
    RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
        override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
            if(response.body()!=null){
                popularItemsLiveData.value=response.body()!!.meals
            }

        }

        override fun onFailure(call: Call<CategoryList>, t: Throwable) {
            Log.d("HomeFragment",t.message.toString())
        }
    })
    }
    fun observeRandomMealLiveData(): LiveData<Meal>{
        return RandomMealLiveData
    }
    fun  observePopularItemsLiveData():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }
}