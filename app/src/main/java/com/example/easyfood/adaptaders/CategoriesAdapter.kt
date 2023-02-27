package com.example.easyfood.adaptaders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.CategoryList

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgViewCategory)

        holder.binding.textCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun setCategoriesList(categoriesList: ArrayList<Category>) {
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}