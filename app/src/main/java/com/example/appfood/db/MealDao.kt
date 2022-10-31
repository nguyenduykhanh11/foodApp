package com.example.appfood.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.appfood.pojo.Meal
import com.example.appfood.utils.Constants

@Dao
interface MealDao {
    @Insert(onConflict = REPLACE)
    suspend fun update(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM ${Constants.MEAL_TABLE_NAME}")
    fun getAllMeal():LiveData<List<Meal>>
}