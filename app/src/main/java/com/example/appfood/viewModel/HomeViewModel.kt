package com.example.appfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.db.MealDatabase
import com.example.appfood.pojo.*
import com.example.appfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase): ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemMealLiveData = MutableLiveData<List<PopularMeals>>()
    private var categoryMealLiveData = MutableLiveData<List<CategoryMeal>>()
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeal()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<Meallist> {
            override fun onResponse(call: Call<Meallist>, response: Response<Meallist>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meallist>, t: Throwable) {
                Log.d("Tag", "thất bại")
            }

        })
    }
    fun getPopularItemMeal(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<PopularMealList>{
            override fun onResponse(call: Call<PopularMealList>, response: Response<PopularMealList>) {
                if(response.body() != null){
                    popularItemMealLiveData.value = response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                Log.d("Tag", "thất bại")
            }

        })
    }
    fun getCategoryMeal(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryMealList>{
            override fun onResponse(
                call: Call<CategoryMealList>,
                response: Response<CategoryMealList>
            ) {
                response.body()?.let { categoryList->
                    categoryMealLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                Log.d("Tag", "thất bại")
            }

        })
    }

    fun observeRandomMeal(): LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemMeal(): LiveData<List<PopularMeals>>{
        return popularItemMealLiveData
    }
    fun observeCategoryItemMeal(): LiveData<List<CategoryMeal>>{
        return categoryMealLiveData
    }

    //Room
    fun observeFavoritesLiveData():LiveData<List<Meal>>{
        return favoritesMealLiveData
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}