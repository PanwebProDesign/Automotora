package com.protectly.automotora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.protectly.automotora.databinding.ViewholderCarsBinding
import com.protectly.automotora.model.Car
import com.squareup.picasso.Picasso

class CarAdapter(private val carList: List<Car>, private val onItemClick: (Car) -> Unit) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ViewholderCarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car, onItemClick)
    }

    override fun getItemCount(): Int = carList.size

    inner class CarViewHolder(private val binding: ViewholderCarsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car, onItemClick: (Car) -> Unit) {
            binding.titlePopularTxt.text = car.title
            binding.price.text = "$${car.price}"
            Picasso.get().load(car.picUrl).into(binding.picPopular)

            itemView.setOnClickListener {
                onItemClick(car)
            }
        }
    }
}
