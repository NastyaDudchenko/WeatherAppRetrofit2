package com.weatherappretrofit2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weatherappretrofit2.R
import com.weatherappretrofit2.model.local.forecast.ForecastListItem

class CustomRecyclerAdapter :
    RecyclerView.Adapter<CustomRecyclerAdapter.ExampleViewHolder>() {
    private val listItems = mutableListOf<ForecastListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item,
            parent, false
        )
        return ExampleViewHolder(itemView)
    }

    fun addItems(forecastList: List<ForecastListItem>) {
        listItems.clear()
        listItems.addAll(forecastList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = listItems[position]

        Glide.with(holder.imageView.context)
            .load(currentItem.imageResource)
            .into(holder.imageView)

        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
        holder.textView3.text = currentItem.text3
    }

    override fun getItemCount() = listItems.size
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.iImage)
        val textView1: TextView = itemView.findViewById(R.id.tvTempForecast)
        val textView2: TextView = itemView.findViewById(R.id.tvDate)
        val textView3: TextView = itemView.findViewById(R.id.tvDescriptionForecast)
    }
}


