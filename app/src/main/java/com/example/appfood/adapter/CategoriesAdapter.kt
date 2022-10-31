package com.example.appfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.databinding.CategoryItemBinding
import com.example.appfood.pojo.CategoryMeal
import com.example.appfood.pojo.PopularMeals

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    lateinit var onClickItem: ((CategoryMeal)->Unit)
    private var categoryList = ArrayList<CategoryMeal>()

    class CategoryViewHolder(var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder.binding){
            Glide.with(holder.itemView)
                .load(categoryList[position].strCategoryThumb)
                .into(imgCategory)
            tvCategoryName.text = categoryList[position].strCategory
        }
        setUpListenerClickItem(holder, position)
    }

    override fun getItemCount(): Int = categoryList.size

    private fun setUpListenerClickItem(holder: CategoriesAdapter.CategoryViewHolder, position: Int) {
        holder.binding.imgCategory .setOnClickListener {
            onClickItem.invoke(categoryList[position])
        }
    }

    fun setCategoryList(categoryList: ArrayList<CategoryMeal>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
}