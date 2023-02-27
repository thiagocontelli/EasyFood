package com.example.easyfood.adaptaders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.imgViewMeal)

        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    class CategoryMealsViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}