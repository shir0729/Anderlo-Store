package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewholderSizeBinding

class SizeAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<SizeAdapter.Viewholder>() {

    private var selectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.sizeTxt.text = items[position]

        holder.binding.root.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg_selected)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.purple, null))
        } else {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.black, null))
        }
    }

    override fun getItemCount(): Int = items.size
}
