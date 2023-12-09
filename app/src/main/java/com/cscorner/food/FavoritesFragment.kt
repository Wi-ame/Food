package com.cscorner.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.food.adapters.MealsAdapter
import com.cscorner.food.databinding.FragmentFavoritesBinding
import com.cscorner.food.videoModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment() : Fragment() {
    private lateinit var binding :FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoritesAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel

    }
  
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()
        observeFavorites()
        val itemTouchHelper=  object :ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION && position < favoritesAdapter.differ.currentList.size) {
                    val deletedMeal = favoritesAdapter.differ.currentList[position]
                    viewModel.deleteMeal(deletedMeal)
                    Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo",
                        View.OnClickListener {
                            viewModel.insertMeal(deletedMeal)
                        }
                    ).show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.tvFavorites)
    }
    private fun prepareRecycleView() {
        favoritesAdapter = MealsAdapter()
        binding.tvFavorites.apply {
            layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoritesAdapter
        }
    }

    private fun observeFavorites() {
       viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer {meals->
         favoritesAdapter.differ.submitList(meals)
       })
    }
}