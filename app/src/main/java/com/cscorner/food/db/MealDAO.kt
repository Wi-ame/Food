package com.cscorner.food.db

import android.util.Log
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
     suspend fun upsert(meal: Meal) {
        try {
            Log.d(TAG, "Inserting meal: $meal")

        } catch (e: Exception) {
            Log.e(TAG, "Error inserting meal: ${e.message}")
            throw e
        }
    }
    @Delete
     suspend fun delete(meal: Meal) {
        try {
            Log.d(TAG, "Deleting meal: $meal")
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting meal: ${e.message}")
            throw e
        }
    }
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>

    companion object {
        private const val TAG = "MealDAO"
    }
}
