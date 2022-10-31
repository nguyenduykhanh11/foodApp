package com.example.appfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appfood.activites.CategoryMealActivity
import com.example.appfood.activites.MainActivity
import com.example.appfood.activites.MealActivity
import com.example.appfood.adapter.CategoriesAdapter
import com.example.appfood.adapter.PopularAdapter
import com.example.appfood.databinding.FragmentHomeBinding
import com.example.appfood.pojo.CategoryMeal
import com.example.appfood.pojo.PopularMeals
import com.example.appfood.pojo.Meal
import com.example.appfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var ramdomMeal: Meal
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    companion object{
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_THUMB = "MEAL_THUMB"
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = (activity as MainActivity).vielModel
        popularAdapter = PopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHomeViewModel.getRandomMeal()
        observeRandomMeal()
        setUpListenerImgRandom()

        PopularAdapterView()
        mHomeViewModel.getPopularItemMeal()
        observePopularItemMeal()
        setUpListenerPopularItemClick()

        mHomeViewModel.getCategoryMeal()
        observeCategoryItemMeal()
        catrgoryAdapterView()
        setUpListenerCategoryItemClick()


    }

    private fun setUpListenerCategoryItemClick() {
        categoriesAdapter.onClickItem = { categoryMeal->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, categoryMeal.strCategory)
            startActivity(intent)
        }
    }

    private fun catrgoryAdapterView() {
        binding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoryItemMeal() {
        mHomeViewModel.observeCategoryItemMeal().observe(viewLifecycleOwner, { categoryMeal->
            categoriesAdapter.setCategoryList(categoryList = categoryMeal as ArrayList<CategoryMeal> )
        })
    }

    private fun setUpListenerPopularItemClick() {
        popularAdapter.onClickItem = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun PopularAdapterView() {
        binding.recyclerViewMealPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observePopularItemMeal() {
        mHomeViewModel.observePopularItemMeal().observe(viewLifecycleOwner,{ meal->
            popularAdapter.setMeals(mealList = meal as ArrayList<PopularMeals> )
        })
    }

    private fun setUpListenerImgRandom() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, ramdomMeal.idMeal)
            intent.putExtra(MEAL_NAME, ramdomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, ramdomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        mHomeViewModel.observeRandomMeal().observe(viewLifecycleOwner, {neal->
            Glide.with(this@HomeFragment)
                .load(neal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            ramdomMeal = neal
        })
    }
}