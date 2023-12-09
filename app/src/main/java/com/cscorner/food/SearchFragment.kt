package com.cscorner.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cscorner.food.adapters.MealsAdapter
import com.cscorner.food.databinding.FragmentSearchBinding
import com.cscorner.food.videoModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var  binding : FragmentSearchBinding
    private lateinit var  viewModel: HomeViewModel
    private lateinit var searchRecycleViewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()
        binding.imgSearchArrow.setOnClickListener(){ searchMeals() }
        observeSearhedMealsLiveData()
        var searchJob: Job? =null
        binding.edSearchBox.addTextChangedListener { searchQuery->
            searchJob?.cancel()
            searchJob =lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }

        }

    private fun observeSearhedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer {
            mealsList->
            searchRecycleViewAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val  searchQuery = binding.edSearchBox.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecycleView() {
        searchRecycleViewAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecycleViewAdapter
        }
    }

}