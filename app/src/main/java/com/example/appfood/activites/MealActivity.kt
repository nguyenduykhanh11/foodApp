package com.example.appfood.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appfood.R
import com.example.appfood.databinding.ActivityMealBinding
import com.example.appfood.db.MealDatabase
import com.example.appfood.fragments.HomeFragment
import com.example.appfood.pojo.Meal
import com.example.appfood.viewModel.HomeViewModel
import com.example.appfood.viewModel.MealViewModel
import com.example.appfood.viewModel.HomeViewModelFactory

class MealActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var nealId:String
    private lateinit var nealName:String
    private lateinit var nealThumb:String
    private lateinit var youtobeLink:String
    private lateinit var mMealViewModel: MealViewModel
    private lateinit var mHomeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase = MealDatabase.getAppDatabase(this)
        val viewModelFactory = HomeViewModelFactory(mealDatabase)
        mMealViewModel = ViewModelProvider(this).get(MealViewModel::class.java)
        mHomeViewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]
        getMealInformationFormIntent()
        setInformationInView()

        mMealViewModel.getMealDetail(nealId)
        observeMealDetails()

        setUpListenerYoutobe()
        setUpListenerFavories()
    }

    private fun setUpListenerFavories() {
        binding.floatAddToFavorite.setOnClickListener {
            insertFav()
        }
    }

    private fun insertFav() {
        mealToSave?.let {
            mHomeViewModel.insertMeal(it)
            Toast.makeText(this, it.strArea.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpListenerYoutobe() {
        binding.imgYoutobe.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtobeLink))
            startActivity(intent)
        }
    }
    private var mealToSave :Meal? = null
    private fun observeMealDetails() {
        mMealViewModel.observeMealDetails().observe(this, object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal = t
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal?.strCategory}"
                binding.tvArea.text = "Category : ${meal?.strArea}"
                binding.tvIntruction.text = "Category : ${meal?.strInstructions}"

                youtobeLink = meal!!.strYoutube.toString()
            }
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(nealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = nealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFormIntent() {
        val intent = intent
        nealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        nealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        nealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

}