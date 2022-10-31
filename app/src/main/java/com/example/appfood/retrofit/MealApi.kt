package com.example.appfood.retrofit

import com.example.appfood.pojo.CategoryMealList
import com.example.appfood.pojo.PopularMealList
import com.example.appfood.pojo.Meallist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<Meallist>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String): Call<Meallist>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String): Call<PopularMealList>

    @GET("categories.php")
    fun getCategories():Call<CategoryMealList>

    @GET("filter.php?")
    fun getMealIsCategory(@Query("c") categoryName: String): Call<PopularMealList>
}