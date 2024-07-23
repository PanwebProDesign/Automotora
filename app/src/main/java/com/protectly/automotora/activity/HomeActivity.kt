package com.protectly.automotora.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.protectly.automotora.adapter.CategoryAdapter
import com.protectly.automotora.adapter.CarAdapter
import com.protectly.automotora.api.ApiClient
import com.protectly.automotora.api.ApiService
import com.protectly.automotora.databinding.ActivityHomeBinding
import com.protectly.automotora.model.Category
import com.protectly.automotora.model.Car
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList: MutableList<Category> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for categories
        binding.recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(categoryList)
        binding.recyclerView2.adapter = categoryAdapter

        // Set up RecyclerView for popular cars with GridLayoutManager
        binding.recyclerPopular.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        fetchCategoriesFromApi()
        fetchPopularCarsFromApi()
    }

    private fun fetchCategoriesFromApi() {
        binding.progressBar.visibility = View.VISIBLE

        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.getCategories()

        call.enqueue(object : Callback<Map<String, List<Category>>> {
            override fun onResponse(call: Call<Map<String, List<Category>>>, response: Response<Map<String, List<Category>>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val categories = data["Category"]
                        if (categories != null) {
                            categoryList.clear()
                            categoryList.addAll(categories)
                            categoryAdapter.notifyDataSetChanged()
                        } else {
                            Log.e("HomeActivity", "Category data is null or empty")
                        }
                    } else {
                        Log.e("HomeActivity", "Response body is null")
                    }
                } else {
                    Log.e("HomeActivity", "Failed to fetch data from API: ${response.code()}")
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<Map<String, List<Category>>>, t: Throwable) {
                Log.e("HomeActivity", "Failed to fetch data from API", t)
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun fetchPopularCarsFromApi() {
        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.getCars()

        call.enqueue(object : Callback<Map<String, List<Car>>> {
            override fun onResponse(call: Call<Map<String, List<Car>>>, response: Response<Map<String, List<Car>>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val cars = data["Cars"]
                        if (cars != null) {
                            val carList = mutableListOf<Car>()
                            carList.addAll(cars)
                            val carAdapter = CarAdapter(carList) { car ->
                                val intent = Intent(this@HomeActivity, DetailActivity::class.java).apply {
                                    putExtra("car", car) // Aquí se pasa el objeto Car como Parcelable
                                }
                                startActivity(intent)
                            }
                            binding.recyclerPopular.adapter = carAdapter
                        } else {
                            Log.e("HomeActivity", "Cars data is null or empty")
                        }
                    } else {
                        Log.e("HomeActivity", "Response body is null")
                    }
                } else {
                    Log.e("HomeActivity", "Failed to fetch data from API: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Map<String, List<Car>>>, t: Throwable) {
                Log.e("HomeActivity", "Failed to fetch data from API", t)
            }
        })
    }
}
