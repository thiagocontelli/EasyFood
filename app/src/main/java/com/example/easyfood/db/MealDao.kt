package com.example.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.easyfood.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM meal_information")
    fun getAllMeals(): LiveData<List<Meal>>
}