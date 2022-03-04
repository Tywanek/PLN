package com.radlab.pln

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlab.pln.models.EuroRate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EuroFragmentViewModel : ViewModel() {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://api.nbp.pl/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: NetworkService = retrofit.create(NetworkService::class.java)

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    fun euroToPLN() {
        val call: Call<EuroRate> = service.getEuroRate()
        call.enqueue(object : Callback<EuroRate> {

            override fun onResponse(call: Call<EuroRate?>, response: Response<EuroRate?>) {
                _response.postValue(response.body()!!.rates[0].mid.toString())
            }

            override fun onFailure(call: Call<EuroRate?>, t: Throwable) {
                _response.postValue("error")
            }
        })
    }


}