package com.example.appfood.activites

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appfood.adapter.CategoryMealIsAdapter
import com.example.appfood.databinding.ActivityCategoryMealBinding
import com.example.appfood.fragments.HomeFragment
import com.example.appfood.pojo.PopularMeals
import com.example.appfood.viewModel.CategoryMealViewModel

class CategoryMealActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var mCategoryMealViewModel: CategoryMealViewModel
    private lateinit var categoryMealIsAdapter: CategoryMealIsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mCategoryMealViewModel = ViewModelProvider(this).get(CategoryMealViewModel::class.java)
        categoryMealApdapterView()
        setUpView()

        setUpListenerMealItemClick()

    }

    private fun setUpListenerMealItemClick() {
        categoryMealIsAdapter.onClickItem = { meal->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setUpView() {
        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)
        mCategoryMealViewModel.getMealIsCategory(categoryName!!)
        mCategoryMealViewModel.observeMealIsCategory().observe(this, { mealList->
            binding.tvCategoryCount.text = "${categoryName}: ${mealList.size}"
            categoryMealIsAdapter.setMealList(mealList)
        })
    }

    private fun categoryMealApdapterView() {
        categoryMealIsAdapter = CategoryMealIsAdapter()
        binding.recyclerViewMealIsAdapter.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealIsAdapter
        }
    }

}