package com.cscorner.food.videoModel

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.livedata.ktx.R
import androidx.lifecycle.viewModelScope
import com.cscorner.food.db.MealDataBase
import com.cscorner.food.pojo.Category
import com.cscorner.food.pojo.CategoryList
import com.cscorner.food.pojo.MealsByCategoryList
import com.cscorner.food.pojo.MealsByCategory
import com.cscorner.food.pojo.Meal
import com.cscorner.food.pojo.MealList
import com.cscorner.food.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(
    private val mealDataBase: MealDataBase
) : ViewModel(){
    private var RandomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData =MutableLiveData<List<MealsByCategory>>()
    private var CategoriesLiveData =MutableLiveData<List<Category>>()
    private var  favoritesMealsLiveData =mealDataBase.mealDao().getAllMeals()
    private var searchedMealsLiveData =MutableLiveData<List<Meal>>()
    init{
        getRandomMeal()
    }
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
    RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
        override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
            if(response.body()!=null){
                popularItemsLiveData.value=response.body()!!.meals
            }

        }

        override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
            Log.d("HomeFragment",t.message.toString())
        }
    })
    }
    fun observeRandomMealLiveData(): LiveData<Meal>{
        return RandomMealLiveData
    }
    fun  observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    response.body()?.let {CategoryList->
                        CategoriesLiveData.postValue(CategoryList.categories)

                    }
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())

            }
        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mealDataBase.mealDao().insertOrUpdate(meal)
            }
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mealDataBase.mealDao().delete(meal)
            }
        }
    }

    fun searchMeals(searchQuery: String)= RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object:Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               val mealsList = response.body()?.meals
                mealsList?.let {
                    searchedMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        }
    )
    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> =searchedMealsLiveData
    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return CategoriesLiveData
    }
    fun  observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }


}