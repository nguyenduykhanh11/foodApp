package com.example.appfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.db.MealDatabase
import com.example.appfood.pojo.Meal
import com.example.appfood.pojo.Meallist
import com.example.appfood.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(): ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<Meallist>{
            override fun onResponse(call: Call<Meallist>, response: Response<Meallist>) {
                if (response.body() != null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meallist>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }
        })
    }
    fun observeMealDetails(): LiveData<Meal>{
        return mealDetailLiveData
    }

}