package com.cscorner.food

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.cscorner.food.databinding.ActivityMealBinding
import com.cscorner.food.pojo.Meal
import com.cscorner.food.videoModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var  mealId:String
    private lateinit var  mealName:String
    private lateinit var  mealThumb :String
    private lateinit var binding :ActivityMealBinding
    private lateinit  var mealMvvm : MealViewModel
    private lateinit var youtubeLink :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        OnYoutubeImageClick()

    }
    private fun OnYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent =Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }


    }
    private fun setInformationInViews(){
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title =mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }
    private fun getMealInformationFromIntent(){
        val intent =intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_Name)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

}
  private fun   observeMealDetailsLiveData(){
      mealMvvm.observeMealDetailsLiveData().observe(this,object :Observer<Meal>{
          override fun onChanged(value: Meal) {
              onResponseCase()
              val meal =value
              binding.tvCategory.text="Category :${meal!!.strCategory}"
              binding.tvArea.text="Area :${meal!!.strArea}"
              binding.tvInstructionsSteps.text=meal.strInstructions

              youtubeLink= meal.strYoutube

          }

      }

      )
  }


    private fun loadingCase(){
        binding.btnAddTofavorites.visibility= View.INVISIBLE
        binding.tvInstruction.visibility= View.INVISIBLE
        binding.tvCategory.visibility= View.INVISIBLE
        binding.tvArea.visibility= View.INVISIBLE
        binding.imgYoutube.visibility= View.INVISIBLE
        binding.progressBar.visibility= View.VISIBLE

    }
    private fun onResponseCase(){
        binding.btnAddTofavorites.visibility= View.VISIBLE
        binding.tvInstruction.visibility= View.VISIBLE
        binding.tvCategory.visibility= View.VISIBLE
        binding.tvArea.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
        binding.progressBar.visibility= View.INVISIBLE
    }
}