package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.*
import com.example.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal>
        get() = _randomMeal

    private var _popularItems = MutableLiveData<List<MealsByCategory>>()
    val popularItems: LiveData<List<MealsByCategory>>
        get() = _popularItems

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals.first()
                    _randomMeal.value = randomMeal
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("meal", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        _popularItems.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("meal", "onFailure: ${t.message}")
                }
            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategoryList().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    _categories.value = it.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("meal", "onFailure: ${t.message}")
            }
        })
    }
}