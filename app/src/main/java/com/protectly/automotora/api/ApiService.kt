package com.protectly.automotora.api

import com.protectly.automotora.model.Car
import com.protectly.automotora.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("elbajondevitoko/archivos/database.json")
    fun getCategories(): Call<Map<String, List<Category>>>

    @GET("elbajondevitoko/archivos/database.json")
    fun getCars(): Call<Map<String, List<Car>>>
}
