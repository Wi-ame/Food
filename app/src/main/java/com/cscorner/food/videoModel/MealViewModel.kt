package com.cscorner.food.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cscorner.food.db.MealDataBase
import com.cscorner.food.pojo.Meal
import com.cscorner.food.pojo.MealList
import com.cscorner.food.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    private val mealDataBase: MealDataBase
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val meals = response.body()?.meals
                    if (meals != null ) {
                        mealDetailsLiveData.value =response.body()!!.meals[0]
                    } else
                        return
                }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("MealActivty", "API call failed: ${t.message}")
            }
        })
    }
    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mealDataBase.mealDao().insertOrUpdate(meal)
            }
        }
    }

}
