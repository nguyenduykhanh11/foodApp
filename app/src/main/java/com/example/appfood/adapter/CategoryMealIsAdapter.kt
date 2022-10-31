package com.example.appfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.databinding.MealItemsBinding
import com.example.appfood.pojo.CategoryMeal
import com.example.appfood.pojo.PopularMeals

class CategoryMealIsAdapter():RecyclerView.Adapter<CategoryMealIsAdapter.CategoryisMealViewHolder>(){
    lateinit var onClickItem: ((PopularMeals)->Unit)
    private var mealList = ArrayList<PopularMeals>()

    inner class CategoryisMealViewHolder(val binding: MealItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryisMealViewHolder {
        return CategoryisMealViewHolder(MealItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryisMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
        setUpListenerClickItem(holder, position)
    }

    private fun setUpListenerClickItem(holder: CategoryMealIsAdapter.CategoryisMealViewHolder, position: Int) {
        holder.binding.imgMeal.setOnClickListener {
            onClickItem.invoke(mealList[position])
        }
        holder.binding.tvMealName.setOnClickListener {
            onClickItem.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int = mealList.size



    fun setMealList(mealList: List<PopularMeals>){
        this.mealList = mealList as ArrayList<PopularMeals>
        notifyDataSetChanged()
    }
}