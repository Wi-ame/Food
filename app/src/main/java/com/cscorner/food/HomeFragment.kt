package com.cscorner.food

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cscorner.food.adapters.MostPopularAdapter
import com.cscorner.food.databinding.FragmentHomeBinding
import com.cscorner.food.pojo.CategoryMeals
import com.cscorner.food.pojo.Meal
import com.cscorner.food.pojo.MealList
import com.cscorner.food.retrofit.RetrofitInstance
import com.cscorner.food.videoModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal :Meal
    private lateinit var popularItemsAdapters : MostPopularAdapter
    companion object {
        const val MEAL_ID="com.cscorner.food.idMeal"
        const val MEAL_Name="com.cscorner.food.nameMeal"
        const val MEAL_THUMB="com.cscorner.food.thumbMeal"
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding =FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      homeMvvm=ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapters= MostPopularAdapter()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparPopularItemRecycleView()
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
       popularItemsAdapters.onItemClick={ meal->
           val intent =Intent(activity,MealActivity::class.java)
           intent.putExtra(MEAL_ID,meal.idMeal)
           intent.putExtra(MEAL_Name,meal.strMeal)
           intent.putExtra(MEAL_THUMB,meal.strMealThumb)
           startActivity(intent)

       }
    }

    private fun preparPopularItemRecycleView() {
        binding.recViewMealsPopular.apply{
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemsAdapters
        }
    }


    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_Name,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }



    private fun observeRandomMeal() {
       homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
       ) { meal ->
           Glide.with(this@HomeFragment)
               .load(meal!!.strMealThumb)
               .into(binding.imgRandomMeal)

           this.randomMeal = meal

       }
    }
     private fun observePopularItemLiveData(){
         homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
         ) { mealList->
             popularItemsAdapters.setMeals(mealslist = mealList as ArrayList<CategoryMeals>)
         }
     }

}