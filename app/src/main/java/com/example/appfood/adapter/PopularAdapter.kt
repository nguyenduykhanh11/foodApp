package com.example.appfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.databinding.PopularItemsBinding
import com.example.appfood.pojo.PopularMeals

class PopularAdapter(): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    lateinit var onClickItem: ((PopularMeals)->Unit)
    private var mealList = ArrayList<PopularMeals>()

    class PopularViewHolder(var binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItems)
        setUpListenerClickItem(holder, position)
    }
    override fun getItemCount(): Int = mealList.size

    private fun setUpListenerClickItem(holder: PopularAdapter.PopularViewHolder, position: Int) {
        holder.binding.imgPopularMealItems.setOnClickListener {
            onClickItem.invoke(mealList[position])
        }
    }

    fun setMeals(mealList: ArrayList<PopularMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }
}