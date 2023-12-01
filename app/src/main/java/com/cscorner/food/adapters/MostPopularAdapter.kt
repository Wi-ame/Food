package com.cscorner.food.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cscorner.food.databinding.PopulerItemsBinding
import com.cscorner.food.pojo.MealsByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private var mealslist =ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)
    fun setMeals(mealslist:ArrayList<MealsByCategory>){
        this.mealslist=mealslist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
         return PopularMealViewHolder(PopulerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
      Glide.with(holder.itemView)
          .load(mealslist[position].strMealThumb)
          .into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealslist[position])
        }
    }

    override fun getItemCount(): Int {
       return mealslist.size
    }

    class PopularMealViewHolder( var binding: PopulerItemsBinding):RecyclerView.ViewHolder(binding.root)
}