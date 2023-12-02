package com.cscorner.food.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cscorner.food.pojo.Meal

@Dao

interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insertMeal(meal:Meal)
     @Delete
       fun delete(meal:Meal)
  @Query("SELECT * FROM mealInformation")
  fun  getAllMeals():LiveData<List<Meal>>
}