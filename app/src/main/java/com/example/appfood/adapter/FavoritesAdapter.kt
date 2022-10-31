package com.example.appfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.databinding.MealItemsBinding
import com.example.appfood.pojo.Meal
import com.example.appfood.pojo.PopularMeals

class FavoritesAdapter():RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    var mealList = ArrayList<Meal>()

    inner class FavoritesViewHolder(var binding: MealItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(MealItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int = mealList.size

    fun setMealList(mealList: List<Meal>){
        this.mealList = mealList as ArrayList<Meal>
        notifyDataSetChanged()
    }
}