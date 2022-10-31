package com.example.appfood.db

import android.content.Context
import androidx.room.*
import com.example.appfood.pojo.Meal
import com.example.appfood.utils.Constants

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
    companion object{
        @Volatile
        var INSTANCE: MealDatabase?=null
        @Synchronized
        fun getAppDatabase(context: Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    Constants.DATABASE_TABLE,
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}