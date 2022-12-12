package com.example.meditation.controller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meditation.common.GlobalHelper
import com.example.meditation.databinding.ItemFeelingsListBinding
import com.example.meditation.databinding.ItemQuotesListBinding
import com.example.meditation.model.FeelingsDataItem
import com.example.meditation.model.QuotesDataItem
import com.example.meditation.model.dataItem

class QuoteListAdapter(
    var data: ArrayList<QuotesDataItem> = arrayListOf()
) : RecyclerView.Adapter<QuoteListAdapter.ViewHolder>() {

    private lateinit var context: Context
    inner class ViewHolder(val binding: ItemQuotesListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemQuotesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("HTTP_QINAD", data.toString())

        holder.binding.quoteTitle.text = data[0].data[position].title
        holder.binding.quoteDescription.text = data[0].data[position].description
        holder.binding.quoteImage.load(data[0].data[position].image)

        holder.binding.quoteMoreButton.setOnClickListener {
            GlobalHelper.Toast(context, "Подробнее")
        }
    }

    override fun getItemCount(): Int {
        return data[0].data.size
    }

    fun update(newData: ArrayList<QuotesDataItem>) {
        data = newData
        notifyDataSetChanged()
    }
}

