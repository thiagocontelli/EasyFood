package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealYtLink: String
    private lateinit var mealVM: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealVM = ViewModelProvider(this)[MealViewModel::class.java]
        getMealInfoFromIntent()
        setInfoInViews()
        loadingCase()
        mealVM.getMealDetails(mealId)
        observeMealDetailsLiveData()
        onYtImageClick()
    }

    private fun onYtImageClick() {
        binding.ytBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYtLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealVM.mealDetails.observe(
            this
        ) {
            responseCase()
            val meal = it
            binding.textCategory.text = "Category: " + meal.strCategory
            binding.textArea.text = "Area: " + meal.strArea
            binding.textInstructions.text = meal.strInstructions
            mealYtLink = meal.strYoutube.toString()
        }
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.floatingBtn.visibility = View.INVISIBLE
        binding.linearLayout.visibility = View.INVISIBLE
        binding.ytBtn.visibility = View.INVISIBLE
        binding.textInstructions.visibility = View.INVISIBLE
        binding.textInstructionsTitle.visibility = View.INVISIBLE
    }

    private fun responseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.floatingBtn.visibility = View.VISIBLE
        binding.linearLayout.visibility = View.VISIBLE
        binding.ytBtn.visibility = View.VISIBLE
        binding.textInstructions.visibility = View.VISIBLE
        binding.textInstructionsTitle.visibility = View.VISIBLE
    }
}