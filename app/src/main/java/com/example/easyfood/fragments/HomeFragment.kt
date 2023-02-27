package com.example.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.activities.CategoryMealsActivity
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adaptaders.CategoriesAdapter
import com.example.easyfood.adaptaders.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.Meal
import com.example.easyfood.pojo.MealsByCategory
import com.example.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.easyfood.fragments.mealId"
        const val MEAL_NAME = "com.example.easyfood.fragments.mealName"
        const val MEAL_THUMB = "com.example.easyfood.fragments.mealThumb"
        const val CATEGORY_NAME = "com.example.easyfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeVM = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        prepareCategoriesRecyclerView()

        homeVM.getRandomMeal()
        observeRandomMeal()
        onRandomMealClicked()

        homeVM.getPopularItems()
        observePopularItems()
        onPopularItemClick()

        homeVM.getCategories()
        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }


    private fun observeCategories() {
        homeVM.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.setCategoriesList(it as ArrayList<Category>)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observePopularItems() {
        homeVM.popularItems.observe(viewLifecycleOwner) {
            popularItemsAdapter.setMeals(it as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClicked() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeVM.randomMeal.observe(
            viewLifecycleOwner
        ) { t ->
            Glide.with(this@HomeFragment).load(t!!.strMealThumb).into(binding.imgRandomMeal)
            this.randomMeal = t
        }
    }

}