package com.cscorner.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cscorner.food.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDAO

    companion object {
        @Volatile
        var INSTANCE: MealDataBase? = null

        @Synchronized
        fun getInstance(context: Context): MealDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    name = "meal.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDataBase
        }


    }
}
