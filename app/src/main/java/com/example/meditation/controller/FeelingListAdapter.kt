package com.example.meditation.controller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meditation.databinding.ItemFeelingsListBinding
import com.example.meditation.model.FeelingsDataItem
import com.example.meditation.model.dataItem

class FeelingListAdapter(
    var data: ArrayList<FeelingsDataItem> = arrayListOf()
) : RecyclerView.Adapter<FeelingListAdapter.ViewHolder>() {

    private lateinit var context: Context
    inner class ViewHolder(val binding: ItemFeelingsListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemFeelingsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("HTTP_FIINAD", data.toString())

        data[0].data.sortedWith(compareBy { it.position })

        holder.binding.feelingName.text = data[0].data[position].title
        holder.binding.feelingImage.load(data[0].data[position].image)
    }

    override fun getItemCount(): Int {
        return data[0].data.size
    }

    fun update(newData: ArrayList<FeelingsDataItem>) {
        data = newData
        notifyDataSetChanged()
    }
}

