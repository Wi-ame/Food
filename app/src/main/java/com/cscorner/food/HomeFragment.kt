package com.cscorner.food

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cscorner.food.adapters.CategoriesAdapter
import com.cscorner.food.adapters.MostPopularAdapter
import com.cscorner.food.databinding.FragmentHomeBinding
import com.cscorner.food.pojo.MealsByCategory
import com.cscorner.food.pojo.Meal
import com.cscorner.food.videoModel.HomeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal :Meal
    private lateinit var popularItemsAdapters : MostPopularAdapter
    private lateinit var categoriesAdapter :CategoriesAdapter
    companion object {
        const val MEAL_ID="com.cscorner.food.idMeal"
        const val MEAL_Name="com.cscorner.food.nameMeal"
        const val MEAL_THUMB="com.cscorner.food.thumbMeal"
        const val CATEGORY_NAME ="com.cscorner.food.CategoryName"

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
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapters= MostPopularAdapter()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparPopularItemRecycleView()
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItemLiveData()
        onPopularItemClick()
        prepareCategoriesRecycleView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()


    }

    private fun onCategoryClick() {
      categoriesAdapter.onItemClick={category ->
          val intent =Intent(activity,CategoryMealsActivity::class.java)
          intent.putExtra(CATEGORY_NAME,category.strCategory)
          startActivity(intent)
      }
    }

    private fun prepareCategoriesRecycleView() {
     binding.recViewCategories.apply {
         categoriesAdapter=CategoriesAdapter()
         layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
         adapter = categoriesAdapter
     }
    }

    private fun observeCategoriesLiveData() {
      viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner , Observer { categories->
          categoriesAdapter.setCategoryList(categories)

      })
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
       viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
       ) { meal ->
           Glide.with(this@HomeFragment)
               .load(meal!!.strMealThumb)
               .into(binding.imgRandomMeal)

           this.randomMeal = meal

       }
    }
     private fun observePopularItemLiveData(){
         viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
         ) { mealList->
             popularItemsAdapters.setMeals(mealslist = mealList as ArrayList<MealsByCategory>)
         }
     }

}