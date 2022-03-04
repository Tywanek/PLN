package com.radlab.pln

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlab.pln.models.EuroRate
import com.radlab.pln.models.Rate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EuroFragmentViewModel : ViewModel() {

    private var euroRate = Rate()
    private val _responsePLN = MutableLiveData<String>()
    private val _responseEUR = MutableLiveData<String>()
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response
    val responseEUR: LiveData<String>
        get() = _responsePLN
    val responsePLN: LiveData<String>
        get() = _responseEUR

    fun calculate(value: String) {
        euroRate.let {
            _responsePLN.postValue((value.toDouble().times(it.mid)).toString())
            _responseEUR.postValue((value.toDouble().div(it.mid).toString()))
        }
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nbp.pl/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: NetworkService = retrofit.create(NetworkService::class.java)
        val call: Call<EuroRate> = service.getEuroRate()
        call.enqueue(object : Callback<EuroRate> {

            override fun onResponse(call: Call<EuroRate?>, response: Response<EuroRate?>) {
                euroRate = response.body()!!.rates[0]
                _response.postValue(euroRate.mid.toString())
            }

            override fun onFailure(call: Call<EuroRate?>, t: Throwable) {
                _response.postValue("error")
            }
        })
    }

}