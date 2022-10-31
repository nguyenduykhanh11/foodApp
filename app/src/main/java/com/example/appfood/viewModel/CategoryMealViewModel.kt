package com.example.appfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appfood.pojo.PopularMealList
import com.example.appfood.pojo.PopularMeals
import com.example.appfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel: ViewModel() {
    private val mealsLiveData = MutableLiveData<List<PopularMeals>>()
    fun getMealIsCategory(categoryName: String){
        RetrofitInstance.api.getMealIsCategory(categoryName).enqueue(object : Callback<PopularMealList>{
            override fun onResponse(
                call: Call<PopularMealList>,
                response: Response<PopularMealList>
            ) {
                response.body()?.let { mealList->
                    mealsLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                Log.d("Tag", "thất bại")
            }

        })
    }

    fun observeMealIsCategory(): LiveData<List<PopularMeals>>{
        return mealsLiveData
    }
}