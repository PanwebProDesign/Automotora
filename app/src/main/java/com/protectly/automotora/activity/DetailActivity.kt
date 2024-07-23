package com.protectly.automotora.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protectly.automotora.databinding.ActivityDetailBinding
import com.protectly.automotora.model.Car
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val car = intent.getParcelableExtra<Car>("car")

        // Mostrar datos en las vistas
        binding.carTitle.text = car?.title
        binding.carDescription.text = car?.description
        binding.carEngineOutput.text = "Engine Output: ${car?.engineOutput}"
        binding.carHighestSpeed.text = "Highest Speed: ${car?.highestSpeed}"
        binding.carPrice.text = "Price: ${car?.price}"
        binding.carRating.text = "Rating: ${car?.rating}"
        binding.carCapacity.text = "Capacity: ${car?.totalCapacity}"

        // Cargar imagen usando Picasso
        Picasso.get().load(car?.picUrl).into(binding.carImage)
    }
}
