package com.mynt.weather.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mynt.weather.R
import com.mynt.weather.data.db.entity.WeatherEntity
import com.mynt.weather.databinding.HistoryItemLayoutBinding
import com.mynt.weather.utils.Utils

class HistoryAdapter(
    var context: Context,
    var weathers: List<WeatherEntity>?
) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: HistoryItemLayoutBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.history_item_layout, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weathers?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherEntity = weathers?.get(position)
        holder.bind(weatherEntity)
    }

    inner class MyViewHolder(var binding: HistoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherEntity: WeatherEntity?) {
            binding.weather = weatherEntity
            binding.util = Utils
        }
    }
}