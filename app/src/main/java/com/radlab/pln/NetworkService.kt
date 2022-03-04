package com.radlab.pln

import com.radlab.pln.models.EuroRate
import retrofit2.Call
import retrofit2.http.GET


interface NetworkService {

    @GET("exchangerates/rates/a/eur?format=json")
    fun getEuroRate(): Call<EuroRate>
}
