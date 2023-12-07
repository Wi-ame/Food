package com.cscorner.food

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.cscorner.food.adapters.FavoritesMealsAdapter
import com.cscorner.food.databinding.FragmentFavoritesBinding
import com.cscorner.food.videoModel.HomeViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding :FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoritesAdapter: FavoritesMealsAdapter

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
    }

    private fun prepareRecycleView() {
        favoritesAdapter = FavoritesMealsAdapter()
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